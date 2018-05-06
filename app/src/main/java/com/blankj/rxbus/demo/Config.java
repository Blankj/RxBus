package com.blankj.rxbus.demo;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/25
 *     desc  :
 * </pre>
 */
public class Config {

    public static String sMsg = "";

    public static void restoreMsg() {
        sMsg = "";
    }

    public static String appendMsg(String msg) {
        if (sMsg.equals("")) {
            sMsg = msg;
        } else {
            sMsg = sMsg + "\n" + msg;
        }
        return sMsg;
    }
}
