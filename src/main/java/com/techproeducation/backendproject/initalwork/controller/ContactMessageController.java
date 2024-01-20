package com.techproeducation.backendproject.initalwork.controller;

import com.techproeducation.backendproject.initalwork.domain.ContactMessage;
import com.techproeducation.backendproject.initalwork.dto.ContactMessageDTO;
import com.techproeducation.backendproject.initalwork.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contact-messages")
public class ContactMessageController {

    @Autowired
    private ContactMessageService service;

    //Create contact messages.
    @PostMapping // http://localhost:8080/contact-messages + POST + JSON (Body)
    public ResponseEntity<Map<String,String>> creatingTheMessages(@Valid @RequestBody ContactMessage  messages){

        service.createContactMessages(messages);

        Map<String,String> createdMessage = new HashMap<>();
        createdMessage.put("message: ", "Contact Messages has been created successfully");

        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }


    //get all contact messages
    @GetMapping // http://localhost:8080/contact-messages + GET
    public ResponseEntity<List<ContactMessage>> getAll(){

        List<ContactMessage> contactMessages = service.getAllContactMessages();
      return new ResponseEntity<>(contactMessages,HttpStatus.OK);
    }

    //get all contact messages by page
    @GetMapping("/page")//  http://localhost:8080/contact-messages/page?p=1&size=3&sort=id&direction=ASC + GET
    public ResponseEntity<Page<ContactMessage>> getAllStudentsWithPage(@RequestParam("p") int pageNum, // Page Number
                                                                @RequestParam("size") int size, // Data amount per page
                                                                @RequestParam("sort") String sort, // prop // Sort by ...
                                                                @RequestParam("direction")Sort.Direction direction // ASC, DESC / Low-to-High, High-to-Low
    ){

        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(direction, sort));

        Page<ContactMessage> studentPage = service.getMessagesWithPage(pageable);

        return ResponseEntity.ok(studentPage);

    }
    //search contact messages by subject

    @GetMapping("/subject/{subject}")// http://localhost:8080/contact-messages/subject/discount
    public ResponseEntity<List<ContactMessage>> getMessagesBySubject(@PathVariable("subject") String subject){
        List<ContactMessage> messagesBySubject = service.getContactMessagesBySubject(subject);

        return ResponseEntity.ok(messagesBySubject);
    }

    //get all contact messages by e-mail
    @GetMapping("/email") //http://localhost:8080/contact-messages/email?email=Ruth123@hotmail.com
    public ResponseEntity<List<ContactMessage>> getMessagesByEmail(@RequestParam("email") String email){
        List<ContactMessage> messagesByEmail = service.contactMessagesByEmail(email);
        return ResponseEntity.ok(messagesByEmail);

    }

    //find all contact messages between two date
    @GetMapping("/betweenDates")//http://localhost:8080/contact-messages/betweenDates?startDate=19.01.2024&endDate=25.01.2024
    public List<ContactMessage> getMessagesBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDateTime endDate) {
        return service.findMessagesBetweenDates(startDate, endDate);
    }
    //find all contact messages between two time
    @GetMapping("/betweenTimes") //http://localhost:8080/contact-messages/betweenTimes?startTime=10:30&endTime=22:00
    public ResponseEntity<List<ContactMessage>> getMessagesByTheirTime(@RequestParam("startTime") LocalTime startTime,
                                                                       @RequestParam("endTime") LocalTime endTime){
        LocalTime start = startTime;
        LocalTime end = endTime;

        List<ContactMessage> messagesByTime = service.getContactMessagesByTime(start,end);

        return ResponseEntity.ok(messagesByTime);
    }

    //delete by ID by using path variable
    @DeleteMapping("/id/{id}") // http://localhost:8080/contact-messages/id/2 
    public ResponseEntity<Map<String, String>> deleteMessage(@PathVariable Long id){

        service.deleteContactMessage(id);

        // Create a Message
        Map<String, String> deleteTheContactMessage = new HashMap<>();
        deleteTheContactMessage.put("message", "Contact Message has been deleted successfully.");
        deleteTheContactMessage.put("status", "true");

        return ResponseEntity.ok(deleteTheContactMessage);
       
    }


    //delete by ID by using request param

    @DeleteMapping("/delete") // http://localhost:8080/contact-messages/delete?id=4
    public ResponseEntity<Map<String, String>> deleteContactMessages(@RequestParam("id") Long id){

        service.deleteMessages(id);

        // Create a Message
        Map<String, String> deleteMessage = new HashMap<>();
        deleteMessage.put("message", "Student has been deleted successfully.");
        deleteMessage.put("status", "true");

        return ResponseEntity.ok(deleteMessage);

    }
    @PutMapping("/update/{id}") //  http://localhost:8080/contact-messages/update/1 + PUT + JSON(Body)
    public ResponseEntity<Map<String, String>> updateContactMessage(@PathVariable Long id, @Valid @RequestBody ContactMessageDTO messageDTO){

        service.updateMessage(id,messageDTO);

        // Create a Message
        Map<String, String> toUpdateMessage = new HashMap<>();
       toUpdateMessage.put("message", "Contact message has been updated successfully!");
        toUpdateMessage.put("status", "true");

        return ResponseEntity.ok(toUpdateMessage);
    }
    
    
    



}
