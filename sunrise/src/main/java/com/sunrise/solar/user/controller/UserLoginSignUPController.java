package com.sunrise.solar.user.controller;

import com.sunrise.solar.user.dto.UserLoginSignUpDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.enums.EnumUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequestMapping("/user/login")
public class UserLoginSignUPController {


    @RequestMapping(value = "/user-profile", method = RequestMethod.POST)
    public ResponseEntity<?> createProfile(
            @RequestBody @NotNull @Valid UserLoginSignUpDTO userLoginSignUpDTO) {


    }


}
