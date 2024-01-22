package com.meenakshiscreens.meenakshiscreensbackend.repository;

import com.meenakshiscreens.meenakshiscreensbackend.entity.Design;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesignRepository extends PagingAndSortingRepository<Design, Long>, JpaSpecificationExecutor<Design> {

    public Optional<Design> findByDesignName(String designName);
}
