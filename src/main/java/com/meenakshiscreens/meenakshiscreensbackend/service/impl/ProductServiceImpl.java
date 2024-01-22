package com.meenakshiscreens.meenakshiscreensbackend.service.impl;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Product;
import com.meenakshiscreens.meenakshiscreensbackend.repository.ProductRepository;
import com.meenakshiscreens.meenakshiscreensbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getByProductName(String productName){
        return productRepository.findByProductName(productName).orElse(null);
    }

    @Override
    public Page<Product> getProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void saveProduct(Product product){
        productRepository.save(product);
    }

    @Override
    public String findProductName(Long id) {
        return productRepository.findProductNameById(id);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
