package com.techproeducation.backendproject.initalwork.dto;

import com.techproeducation.backendproject.initalwork.domain.ContactMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
public class ContactMessageDTO {

    @NotNull(message = "message cannot be null")
    private String message;

    public ContactMessageDTO(ContactMessage message) {
        this.message = message.getMessage();
    }

}
