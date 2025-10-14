package com.controller;

import com.DAO.UserDAO;
import com.model.User;
public class LoginController {
     private final UserDAO userDAO = new UserDAO();

    public User authenticate(String username, String password) {
        // si usas hash, hashea aquí antes de pasar al DAO
        return userDAO.loginUser(username, password);
    }
}
