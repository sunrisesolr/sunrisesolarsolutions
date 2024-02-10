package com.sunrise.solar.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubmitFormDTO {
    @NotNull
    String name;
    String emailAddress;
    @NotBlank(message = "mobile is required")
    String phoneNumber;
    String location;
    String comments;
}
