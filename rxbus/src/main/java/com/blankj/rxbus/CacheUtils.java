package com.blankj.rxbus;

import java.util.ArrayList;
import java.util.HashMap;
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

    synchronized void addStickyEvent(final TagMessage stickyEvent) {
        Class<?> eventType = stickyEvent.event.getClass();
        synchronized (stickyEventsMap) {
            List<TagMessage> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents == null) {
                stickyEvents = new ArrayList<>();
                stickyEvents.add(stickyEvent);
                stickyEventsMap.put(eventType, stickyEvents);
            } else {
                int indexOf = stickyEvents.indexOf(stickyEvent);
                if (indexOf == -1) {// 不存在直接插入
                    stickyEvents.add(stickyEvent);
                } else {// 存在则覆盖
                    stickyEvents.set(indexOf, stickyEvent);
                }
            }
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
        synchronized(disposablesMap) {
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
        synchronized(disposablesMap) {
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
