package com.controller.util;

import com.model.User;

public class SessionData {
    private static User loggedUser;

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void clearSession() {
        loggedUser = null;
    }
}

