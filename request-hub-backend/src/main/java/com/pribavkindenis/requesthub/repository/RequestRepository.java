package com.pribavkindenis.requesthub.repository;

import com.pribavkindenis.requesthub.model.jpa.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findById(Long id);
}
