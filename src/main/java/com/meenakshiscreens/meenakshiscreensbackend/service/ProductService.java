package com.meenakshiscreens.meenakshiscreensbackend.service;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    public Product getByProductName(String productName);

    public Page<Product> getProducts(Pageable pageable);

    public Product getById(Long id);

    public void saveProduct(Product product);

    public String findProductName(Long id);

    public void deleteProduct(Long id);
}
