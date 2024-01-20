package com.techproeducation.backendproject.initalwork.repository;

import com.techproeducation.backendproject.initalwork.domain.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {


    List<ContactMessage> findAllBySubject(String subject);

    List<ContactMessage> findByEmail(String email);


    @Query("SELECT cm FROM ContactMessage cm WHERE cm.creationDate BETWEEN :startDate AND :endDate")
    List<ContactMessage> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT c FROM ContactMessage c WHERE " +
            "(c.creationDate >= :startTime AND c.creationDate <= :endTime)")
    List<ContactMessage> findByTimeBetween(
            @Param("startTime") LocalTime start,
            @Param("endTime") LocalTime end
    );

    Optional<ContactMessage> findById(Long id);


}
