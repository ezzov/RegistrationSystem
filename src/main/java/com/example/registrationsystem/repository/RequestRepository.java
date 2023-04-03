package com.example.registrationsystem.repository;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllByUserOrderByDateOfCreationAsc(User user, Pageable pageable);

    Page<Request> findAllByUserOrderByDateOfCreationDesc(User user, Pageable pageable);

}
