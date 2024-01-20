package com.techproeducation.backendproject.initalwork.service;

import com.techproeducation.backendproject.initalwork.domain.ContactMessage;
import com.techproeducation.backendproject.initalwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initalwork.exception.ResourceNotFoundException;
import com.techproeducation.backendproject.initalwork.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository repository;



    public void createContactMessages(ContactMessage messages) {
     repository.save(messages);
    }

    public List<ContactMessage> getAllContactMessages() {
        return repository.findAll();

    }

    public Page<ContactMessage> getMessagesWithPage(Pageable page) {
        return  repository.findAll(page);
    }


    public List<ContactMessage> getContactMessagesBySubject(String subject) {
        return repository.findAllBySubject(subject);
    }

    public List<ContactMessage> contactMessagesByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<ContactMessage> findMessagesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByCreationDateBetween(startDate, endDate);
    }
    @Query("SELECT new com.techproeducation.backendproject.initalwork.ContactMessage(c.id, c.name, c.message, c.subject, c.email, c.creationDate) " +
            "FROM ContactMessage c WHERE c.creationDate BETWEEN :startTime AND :endTime")
    public List<ContactMessage> getContactMessagesByTime(
            @Param("startTime") LocalTime start,
            @Param("endTime") LocalTime end) {
        return repository.findByTimeBetween(start, end);
    }

    public ContactMessage getContactMessage(Long id) {

        ContactMessage contactMessage = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Student not found with ID: "+id)
        );  //It gives an error since data type is optional

        return contactMessage;

    }

    public void deleteContactMessage(Long id) {


        ContactMessage message = getContactMessage(id);

        repository.deleteById(id);

    }

    public void deleteMessages(Long id) {

        repository.deleteById(id);
    }

    public void updateMessage(Long id, ContactMessageDTO messageDTO) {

        ContactMessage messageDb = getContactMessage(id);

        repository.save(mapContactMessageDTOToContactMessage(messageDb,messageDTO));
    }

    private ContactMessage mapContactMessageDTOToContactMessage(ContactMessage message,ContactMessageDTO messageDTO) {

        message.setMessage(messageDTO.getMessage());
        return message;
    }


}
