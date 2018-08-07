package com.blankj.rxbus;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/14
 *     desc  :
 * </pre>
 */
final class TagMessage {

    Object mEvent;
    String mTag;

    TagMessage(Object event, String tag) {
        mEvent = event;
        mTag = tag;
    }

    boolean isSameType(final Class eventType, final String tag) {
        return Utils.equals(getEventType(), eventType)
                && Utils.equals(this.mTag, tag);
    }

    Class getEventType() {
        return Utils.getClassFromObject(mEvent);
    }

    @Override
    public String toString() {
        return "event: " + mEvent + ", tag: " + mTag;
    }
}
