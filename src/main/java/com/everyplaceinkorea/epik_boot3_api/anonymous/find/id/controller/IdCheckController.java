package com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.controller;

import com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.dto.IdCheckRequestDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.service.IdCheckService;
import com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto.EmailCheckDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("find")
public class IdCheckController {

    @Autowired
    private IdCheckService idCheckService;

    //3. email 인증 확인
    @PostMapping("/checkEmail")
    public Map<String, String> sendemail(
            @RequestBody EmailCheckDto emailCheckDto
    ){
        return idCheckService.emailCheck(emailCheckDto);
    }


 @PostMapping("/id")
    public String idCheck(@RequestBody IdCheckRequestDto idCheckRequestDto) {
        String usernameResult = idCheckService.idCheck(idCheckRequestDto);
     System.out.println("usernameResult--"+usernameResult);
        return usernameResult ;
 }


}
