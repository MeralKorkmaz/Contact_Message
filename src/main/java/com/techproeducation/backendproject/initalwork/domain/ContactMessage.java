package com.techproeducation.backendproject.initalwork.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "message cannot be null")
    private String message;

    @NotNull(message = "message cannot be null")
    private String subject;

    @Email(message = "invalid email format")
    @NotNull
    private String email;

    @Setter(AccessLevel.NONE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "US")
    private LocalDateTime creationDate = LocalDateTime.now();


}
