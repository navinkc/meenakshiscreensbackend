package com.meenakshiscreens.meenakshiscreensbackend.repository;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    public Optional<Category> findByCategoryName(String categoryName);


}
