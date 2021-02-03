package com.zolostays.instagram.context;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    private static ThreadLocal<Map<Object, Object>> attributes = new ThreadLocal<>();

    public static void intialize(){
        attributes.set(new HashMap<>());
    }

    public static <T> T getAttribute(Object key){
        return (T) attributes.get().get(key);
    }

    public static void cleanup() {
        attributes.set(null);
    }

    public static void setAttributes(Object key, Object value){
        attributes.get().put(key, value);
    }
}
