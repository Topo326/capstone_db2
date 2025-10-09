/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

/**
 *
 * @author Maro
 */
public class LoginController {
     private final com.dao.UserDAO userDAO = new com.dao.UserDAO();

    public com.model.User authenticate(String username, String password) {
        // si usas hash, hashea aquí antes de pasar al DAO
        return userDAO.loginUser(username, password);
    }
}
