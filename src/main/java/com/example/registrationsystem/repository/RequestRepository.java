package com.example.registrationsystem.repository;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllByUserOrderByDateOfCreationAsc(User user, Pageable pageable);

    Page<Request> findAllByUserOrderByDateOfCreationDesc(User user, Pageable pageable);

    @Query(value = "SELECT r FROM Request r WHERE r.status = 'SENT' ORDER BY r.dateOfCreation")
    Page<Request> findAllSentRequestsOrderByDateOfCreationAsc(Pageable pageable);

    @Query(value = "SELECT r FROM Request r WHERE r.status = 'SENT' ORDER BY r.dateOfCreation DESC")
    Page<Request> findAllSentRequestsOrderByDateOfCreationDesc(Pageable pageable);

    @Query(value = "SELECT r FROM Request r JOIN User u ON r.user.id = u.id " +
            "WHERE r.status = 'SENT' AND upper(u.userName) LIKE CONCAT('%', upper(?1), '%') ORDER BY r.dateOfCreation")
    Page<Request> findSentRequestsByUserOrderByCreationAsc(String userName, Pageable pageable);

    @Query(value = "SELECT r FROM Request r JOIN User u ON r.user.id = u.id " +
            "WHERE r.status = 'SENT' AND upper(u.userName) LIKE CONCAT('%', upper(?1), '%') ORDER BY r.dateOfCreation DESC")
    Page<Request> findSentRequestsByUserOrderByCreationDesc(String userName, Pageable pageable);
}
