package com.blankj.rxbus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/25
 *     desc  :
 * </pre>
 */
final class CacheUtils {

    private final Map<Class, List<TagMessage>> stickyEventsMap = new ConcurrentHashMap<>();

    private final Map<Object, List<Disposable>> disposablesMap = new ConcurrentHashMap<>();

    private CacheUtils() {

    }

    public static CacheUtils getInstance() {
        return Holder.CACHE_UTILS;
    }

    void addStickyEvent(final Object event, final String tag) {
        Class eventType = Utils.getClassFromObject(event);
        synchronized (stickyEventsMap) {
            List<TagMessage> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents == null) {
                stickyEvents = new ArrayList<>();
                stickyEvents.add(new TagMessage(event, tag));
                stickyEventsMap.put(eventType, stickyEvents);
            } else {
                for (int i = stickyEvents.size() - 1; i >= 0; --i) {
                    TagMessage tmp = stickyEvents.get(i);
                    if (tmp.isSameType(eventType, tag)) {
                        Utils.logW("The sticky event already added.");
                        return;
                    }
                }
                stickyEvents.add(new TagMessage(event, tag));
            }
        }
    }

    void removeStickyEvent(final Object event, final String tag) {
        Class eventType = Utils.getClassFromObject(event);
        synchronized (stickyEventsMap) {
            List<TagMessage> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents == null) return;
            for (int i = stickyEvents.size() - 1; i >= 0; --i) {
                TagMessage stickyEvent = stickyEvents.get(i);
                if (stickyEvent.isSameType(eventType, tag)) {
                    stickyEvents.remove(i);
                    break;
                }
            }
            if (stickyEvents.size() == 0) stickyEventsMap.remove(eventType);
        }
    }

    TagMessage findStickyEvent(final Class eventType, final String tag) {
        synchronized (stickyEventsMap) {
            List<TagMessage> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents == null) return null;
            int size = stickyEvents.size();
            TagMessage res = null;
            for (int i = size - 1; i >= 0; --i) {
                TagMessage stickyEvent = stickyEvents.get(i);
                if (stickyEvent.isSameType(eventType, tag)) {
                    res = stickyEvents.get(i);
                    break;
                }
            }
            return res;
        }
    }

    void addDisposable(Object subscriber, Disposable disposable) {
        synchronized (disposablesMap) {
            List<Disposable> list = disposablesMap.get(subscriber);
            if (list == null) {
                list = new ArrayList<>();
                list.add(disposable);
                disposablesMap.put(subscriber, list);
            } else {
                list.add(disposable);
            }
        }
    }

    void removeDisposables(final Object subscriber) {
        synchronized (disposablesMap) {
            List<Disposable> disposables = disposablesMap.get(subscriber);
            if (disposables == null) return;
            for (Disposable disposable : disposables) {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
            disposables.clear();
            disposablesMap.remove(subscriber);
        }
    }

    private static class Holder {
        private static final CacheUtils CACHE_UTILS = new CacheUtils();
    }
}
