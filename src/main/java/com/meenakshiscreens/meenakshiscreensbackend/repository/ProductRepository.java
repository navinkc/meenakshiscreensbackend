package com.meenakshiscreens.meenakshiscreensbackend.repository;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    public Optional<Product> findByProductName(String productName);

    public String findProductNameById(Long id);
}
