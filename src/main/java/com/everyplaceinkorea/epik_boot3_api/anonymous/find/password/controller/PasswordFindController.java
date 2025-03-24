package com.everyplaceinkorea.epik_boot3_api.anonymous.find.password.controller;


import com.everyplaceinkorea.epik_boot3_api.anonymous.find.password.dto.PasswordFindRequestDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.find.password.service.PasswordFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("find/password")
public class PasswordFindController {

    @Autowired
    private PasswordFindService passwordFindService;

    @PostMapping("/checkUsername")
    public String checkUsername(@RequestBody PasswordFindRequestDto passwordFindRequestDto) {
        System.out.println("컨트롤럴passwordFindRequestDto--"+passwordFindRequestDto.getUsername());
        return passwordFindService.checkUsername(passwordFindRequestDto);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordFindRequestDto passwordFindRequestDto) {
        return passwordFindService.passwordReset(passwordFindRequestDto);

    }
}
