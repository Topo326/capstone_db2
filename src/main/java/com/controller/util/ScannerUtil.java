package com.controller.util;

import java.util.Scanner;

public class ScannerUtil {
    private final static Scanner instance = new Scanner(System.in);
    public static Scanner getInstance() {
        return instance;
    }
    public static void close() {
        instance.close();
    }
}
