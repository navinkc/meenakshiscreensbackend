package com.meenakshiscreens.meenakshiscreensbackend.controller;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Category;
import com.meenakshiscreens.meenakshiscreensbackend.entity.Product;
import com.meenakshiscreens.meenakshiscreensbackend.entity.request.CategoryRequest;
import com.meenakshiscreens.meenakshiscreensbackend.exception.DuplicateEntityException;
import com.meenakshiscreens.meenakshiscreensbackend.exception.EntityNotFoundException;
import com.meenakshiscreens.meenakshiscreensbackend.exception.RequestValidationException;
import com.meenakshiscreens.meenakshiscreensbackend.service.CategoryService;
import com.meenakshiscreens.meenakshiscreensbackend.service.DesignService;
import com.meenakshiscreens.meenakshiscreensbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableHypermediaSupport(type = { EnableHypermediaSupport.HypermediaType.HAL })
@RequestMapping(value = "/api/private/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DesignService designService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PagedResourcesAssembler<Category> designPagedResourcesAssembler;

    @GetMapping("")
    public ResponseEntity<?> getCategories(@PageableDefault(size = Integer.MAX_VALUE)Pageable pageable){
        Page<Category> categories = categoryService.getCategories(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        Category category = categoryService.getById(id);
        if(category == null) {
            return new ResponseEntity<>(String.format("Category with %s id not found.", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        Product product = productService.getById(categoryRequest.getProductId());
        if (product == null) {
            throw new RequestValidationException(String.format("Product %s id cannot does not exists.", categoryRequest.getProductId()));
        }
        Category existingCategory = categoryService.getByCategoryName(categoryRequest.getCategoryName());
        if (existingCategory != null){
            throw new DuplicateEntityException(String.format("Category with %s name already exists.", categoryRequest.getCategoryName()));
        }
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setProduct(product);
        categoryService.saveCategory(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        Category categoryToUpdate = categoryService.getById(id);
        if (categoryToUpdate == null) {
            throw new EntityNotFoundException(String.format("Category with %s id not found.", id));
        }
        if (!ObjectUtils.isEmpty(categoryRequest.getProductId())) {
            Product productToUpdate = productService.getById(categoryRequest.getProductId());
            if (productToUpdate == null) {
                throw new EntityNotFoundException(String.format("Product with %s id not found.", categoryRequest.getProductId()));
            }
            categoryToUpdate.setProduct(productToUpdate);
        }
        if (categoryRequest.getCategoryName() != null || !ObjectUtils.isEmpty(categoryRequest.getCategoryName())) {
            categoryToUpdate.setCategoryName(categoryRequest.getCategoryName());
        }
        categoryService.saveCategory(categoryToUpdate);
        return new ResponseEntity<>(categoryToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Category categoryToDelete = categoryService.getById(id);
        if (categoryToDelete == null) {
            throw new EntityNotFoundException(String.format("Category with %s id not found.", id));
        }
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(String.format("The category with %s id is currently in use.", id), HttpStatus.IM_USED);
        }
    }
}
