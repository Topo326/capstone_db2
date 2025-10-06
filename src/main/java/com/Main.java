package com;

import com.controller.util.HibernateUtil;
import com.controller.util.ConnectionTester;

public class Main {
    public static void main(String[] args) {
        ConnectionTester.testConnection();
    }
}
