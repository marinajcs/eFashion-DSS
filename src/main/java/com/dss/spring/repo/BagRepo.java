package com.dss.spring.repo;

import com.dss.spring.model.Bag;
import com.dss.spring.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BagRepo extends JpaRepository<Bag, Long> {
    Optional<Bag> findByUser(User user);
}

