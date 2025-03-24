package com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.service;

import com.everyplaceinkorea.epik_boot3_api.anonymous.find.id.dto.IdCheckRequestDto;
import com.everyplaceinkorea.epik_boot3_api.anonymous.signup.dto.EmailCheckDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IdCheckService {

    Map<String, String> emailCheck(EmailCheckDto emailCheckDto);

    String idCheck(IdCheckRequestDto idCheckRequestDto);
}
