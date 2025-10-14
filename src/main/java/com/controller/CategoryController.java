package com.controller;

import com.DAO.CategoryDAO;
import com.model.Category;

import java.util.List;

public class CategoryController {
    private final CategoryDAO dao = new CategoryDAO();

    public List<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    public void addCategory(String name, String description) {
        Category newCategory = new Category(name, description);
        dao.addCategory(newCategory);
    }
}
