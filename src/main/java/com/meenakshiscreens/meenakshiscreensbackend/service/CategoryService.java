package com.meenakshiscreens.meenakshiscreensbackend.service;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Category;
import com.meenakshiscreens.meenakshiscreensbackend.entity.Product;
import com.meenakshiscreens.meenakshiscreensbackend.entity.request.CategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    /**
     * To get the AccessChannel with the given name.
     *
     * @param categoryName
     * @return design
     */
    public Category getByCategoryName(String categoryName);

    public Page<Category> getCategories(Pageable pageable);

    public Category getById(Long id);

    public void saveCategory(Category category);

    public void deleteCategory(Long id);
}
