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

}
