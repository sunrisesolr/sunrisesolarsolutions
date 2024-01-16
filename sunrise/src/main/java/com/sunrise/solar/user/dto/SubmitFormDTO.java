package com.sunrise.solar.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubmitFormDTO {
    String name;
    String emailAddress;
    String phoneNumber;
    String location;
    String comments;
}
