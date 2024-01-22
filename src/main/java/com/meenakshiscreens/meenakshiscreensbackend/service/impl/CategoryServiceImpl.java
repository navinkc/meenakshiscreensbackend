package com.meenakshiscreens.meenakshiscreensbackend.service.impl;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Category;
import com.meenakshiscreens.meenakshiscreensbackend.entity.Product;
import com.meenakshiscreens.meenakshiscreensbackend.repository.CategoryRepository;
import com.meenakshiscreens.meenakshiscreensbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getByCategoryName(String categoryName){
        return categoryRepository.findByCategoryName(categoryName).orElse(null);
    }

    @Override
    public Category getById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Category> getCategories(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    @Override
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
