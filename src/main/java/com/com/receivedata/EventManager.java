package com.com.receivedata;

/**
 * Created by wei on 2018/9/17.
 */

public class EventManager {

    private static Event mEvent;

    public static void setEventListener(Event ms) {
        mEvent = ms;
    }

    public static void raiseEvent(String msg){

        mEvent.onSomthingHappend(msg);

    }

}
