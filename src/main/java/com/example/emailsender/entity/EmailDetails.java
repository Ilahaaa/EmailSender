package com.example.emailsender.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailDetails {
    @NonNull
    private String recipient;
    private String msgBody;
    private String subject;


    private String name;
}