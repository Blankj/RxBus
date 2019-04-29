package com.blankj.rxbus;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/14
 *     desc  : 1.5
 * </pre>
 */
public final class RxBus {

    private final FlowableProcessor<Object> mBus;

    private final Consumer<Throwable> mOnError = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) {
            Utils.logE(throwable.toString());
        }
    };

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        return Holder.BUS;
    }

    public void post(final Object event) {
        post(event, "", false);
    }

    public void post(final Object event, final String tag) {
        post(event, tag, false);
    }

    public void postSticky(final Object event) {
        post(event, "", true);
    }

    public void postSticky(final Object event, final String tag) {
        post(event, tag, true);
    }

    private void post(final Object event,
                      final String tag,
                      final boolean isSticky) {
        Utils.requireNonNull(event, tag);

        TagMessage msgEvent = new TagMessage(event, tag);
        if (isSticky) {
            CacheUtils.getInstance().addStickyEvent(event, tag);
        }
        mBus.onNext(msgEvent);
    }

    public void removeSticky(final Object event) {
        removeSticky(event, "");
    }

    public void removeSticky(final Object event,
                             final String tag) {
        Utils.requireNonNull(event, tag);

        CacheUtils.getInstance().removeStickyEvent(event, tag);
    }

    public <T> void subscribe(final Object subscriber,
                              final Callback<T> callback) {
        subscribe(subscriber, "", false, null, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final String tag,
                              final Callback<T> callback) {
        subscribe(subscriber, tag, false, null, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final Scheduler scheduler,
                              final Callback<T> callback) {
        subscribe(subscriber, "", false, scheduler, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final String tag,
                              final Scheduler scheduler,
                              final Callback<T> callback) {
        subscribe(subscriber, tag, false, scheduler, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final Callback<T> callback) {
        subscribe(subscriber, "", true, null, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final String tag,
                                    final Callback<T> callback) {
        subscribe(subscriber, tag, true, null, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final Scheduler scheduler,
                                    final Callback<T> callback) {
        subscribe(subscriber, "", true, scheduler, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final String tag,
                                    final Scheduler scheduler,
                                    final Callback<T> callback) {
        subscribe(subscriber, tag, true, scheduler, callback);
    }

    private <T> void subscribe(final Object subscriber,
                               final String tag,
                               final boolean isSticky,
                               final Scheduler scheduler,
                               final Callback<T> callback) {
        Utils.requireNonNull(subscriber, tag, callback);

        final Class<T> typeClass = Utils.getTypeClassFromParadigm(callback);

        final Consumer<T> onNext = new Consumer<T>() {
            @Override
            public void accept(T t) {
                callback.onEvent(t);
            }
        };

        if (isSticky) {
            final TagMessage stickyEvent = CacheUtils.getInstance().findStickyEvent(typeClass, tag);
            if (stickyEvent != null) {
                Flowable<T> stickyFlowable = Flowable.create(new FlowableOnSubscribe<T>() {
                    @Override
                    public void subscribe(FlowableEmitter<T> emitter) {
                        emitter.onNext(typeClass.cast(stickyEvent.mEvent));
                    }
                }, BackpressureStrategy.LATEST);
                if (scheduler != null) {
                    stickyFlowable = stickyFlowable.observeOn(scheduler);
                }
                Disposable stickyDisposable = FlowableUtils.subscribe(stickyFlowable, onNext, mOnError);
                CacheUtils.getInstance().addDisposable(subscriber, stickyDisposable);
            } else {
                Utils.logW("sticky event is empty.");
            }
        }
        Disposable disposable = FlowableUtils.subscribe(
                toFlowable(typeClass, tag, scheduler), onNext, mOnError
        );
        CacheUtils.getInstance().addDisposable(subscriber, disposable);
    }

    private <T> Flowable<T> toFlowable(final Class<T> eventType,
                                       final String tag,
                                       final Scheduler scheduler) {
        Flowable<T> flowable = mBus.ofType(TagMessage.class)
                .filter(new Predicate<TagMessage>() {
                    @Override
                    public boolean test(TagMessage tagMessage) {
                        return tagMessage.isSameType(eventType, tag);
                    }
                })
                .map(new Function<TagMessage, Object>() {
                    @Override
                    public Object apply(TagMessage tagMessage) {
                        return tagMessage.mEvent;
                    }
                })
                .cast(eventType);
        if (scheduler != null) {
            return flowable.observeOn(scheduler);
        }
        return flowable;
    }

    public void unregister(final Object subscriber) {
        CacheUtils.getInstance().removeDisposables(subscriber);
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    public abstract static class Callback<T> {
        public abstract void onEvent(T t);
    }
}