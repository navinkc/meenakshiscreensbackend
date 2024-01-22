package com.meenakshiscreens.meenakshiscreensbackend.repository;

import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    public Optional<User> findByUserName(String userName);

    public Optional<User> findByEmail(String email);

}
