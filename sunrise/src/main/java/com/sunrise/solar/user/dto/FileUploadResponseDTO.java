package com.sunrise.solar.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class FileUploadResponseDTO {

    String fileName;
    String status;
    String message;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
