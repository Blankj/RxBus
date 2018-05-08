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

    Object event;
    String tag;

    TagMessage(Object event, String tag) {
        this.event = event;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof TagMessage) {
            TagMessage tagMessage = (TagMessage) obj;
            return Utils.equals(tagMessage.event.getClass(), event.getClass())
                    && Utils.equals(tagMessage.tag, tag);
        }
        return false;
    }

    boolean isSameType(final Class eventType, final String tag) {
        return Utils.equals(event.getClass(), eventType)
                && Utils.equals(this.tag, tag);
    }

    @Override
    public String toString() {
        return "event: " + event + ", tag: " + tag;
    }
}
