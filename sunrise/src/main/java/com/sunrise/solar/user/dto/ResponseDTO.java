package com.sunrise.solar.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class ResponseDTO {
    String name;
    String emailAddress;
    String phoneNumber;
    String location;
    String comments;
    String status;

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ResponseDTO(String comments) {
        this.comments = comments;
    }

    public ResponseDTO(String name, String comments) {
        this.name = name;
        this.comments = comments;
    }

    public ResponseDTO(String name, String emailAddress, String comments) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.comments = comments;
    }

    public ResponseDTO(String name, String emailAddress, String phoneNumber, String comments) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.comments = comments;
    }

    public ResponseDTO(String name, String emailAddress, String phoneNumber, String location, String comments) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.comments = comments;
    }

    public ResponseDTO(String name, String emailAddress, String phoneNumber, String location, String comments, String status) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.comments = comments;
        this.status = status;
    }
}
