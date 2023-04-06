package com.example.registrationsystem.repository;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository that stores {@link Request} entities
 */
public interface RequestRepository extends JpaRepository<Request, Long> {

    /**
     * Method that finds all requests of user sorted by creation date ASC
     * @param user {@link User}
     * @param pageable {@link Pageable}
     * @return {@link Page} of {@link Request}
     */
    Page<Request> findAllByUserOrderByDateOfCreationAsc(User user, Pageable pageable);

    /**
     * Method that finds all requests of user sorted by creation date DESC
     * @param user {@link User}
     * @param pageable {@link Pageable}
     * @return {@link Page} of {@link Request}
     */
    Page<Request> findAllByUserOrderByDateOfCreationDesc(User user, Pageable pageable);

    /**
     * Method that finds all requests with status SENT sorted by creation date ASC
     * @param pageable {@link Pageable}
     * @return {@link Page} of {@link Request}
     */
    @Query(value = "SELECT r FROM Request r WHERE r.status = 'SENT' ORDER BY r.dateOfCreation")
    Page<Request> findAllSentRequestsOrderByDateOfCreationAsc(Pageable pageable);

    /**
     * Method that finds all requests with status SENT sorted by creation date DESC
     * @param pageable {@link Pageable}
     * @return {@link Page} of {@link Request}
     */
    @Query(value = "SELECT r FROM Request r WHERE r.status = 'SENT' ORDER BY r.dateOfCreation DESC")
    Page<Request> findAllSentRequestsOrderByDateOfCreationDesc(Pageable pageable);

    /**
     * Method that finds all requests with status SENT by username or a part of username sorted by creation date ASC
     * @param userName from {@link User}
     * @param pageable {@link Pageable}
     * @return {@link Page} of {@link Request}
     */
    @Query(value = "SELECT r FROM Request r JOIN User u ON r.user.id = u.id " +
            "WHERE r.status = 'SENT' AND upper(u.userName) LIKE CONCAT('%', upper(?1), '%') ORDER BY r.dateOfCreation")
    Page<Request> findSentRequestsByUserOrderByCreationAsc(String userName, Pageable pageable);

    /**
     * Method that finds all requests with status SENT by username or a part of username sorted by creation date DESC
     * @param userName from {@link User}
     * @param pageable {@link Pageable}
     * @return {@link Page} of {@link Request}
     */
    @Query(value = "SELECT r FROM Request r JOIN User u ON r.user.id = u.id " +
            "WHERE r.status = 'SENT' AND upper(u.userName) LIKE CONCAT('%', upper(?1), '%') ORDER BY r.dateOfCreation DESC")
    Page<Request> findSentRequestsByUserOrderByCreationDesc(String userName, Pageable pageable);
}
