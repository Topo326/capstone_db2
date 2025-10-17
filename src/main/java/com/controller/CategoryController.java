package com.controller;

import com.DAO.CategoryDAO;
import com.model.Category;

import java.util.List;

public class CategoryController {
    private final CategoryDAO dao = new CategoryDAO();

    public List<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    public void editCategory(int categoryId, String newName, String newDescription) {
        Category category = dao.findCategoryById(categoryId);
        if (category != null) {
            if (newName != null && !newName.trim().isEmpty()) {
                category.setCategoryName(newName.trim());
            }
            if (newDescription != null) {
                category.setDescription(newDescription);
            }
            dao.updateCategory(category);
        } else {
            System.out.println("Categoría no encontrada: " + categoryId);
        }
    }

    public void addCategory(String name, String description) {
        Category newCategory = new Category(name, description);
        dao.addCategory(newCategory);
    }

    public Category findOrCreateDefaultCategory() {
        List<Category> categories = dao.getAllCategories();

        for (Category c : categories) {
            if (c.getCategoryName().equalsIgnoreCase("General")) {
                return c; // Ya existe la categoría por defecto
            }
        }

        // Si no existe, la crea automáticamente
        Category defaultCategory = new Category("General", "Categoría base por defecto");
        dao.addCategory(defaultCategory);
        return defaultCategory;
    }
}
