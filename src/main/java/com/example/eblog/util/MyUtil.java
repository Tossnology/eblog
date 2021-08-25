package com.example.eblog.util;

public class MyUtil {
    public static String methodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
