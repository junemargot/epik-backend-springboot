package com.everyplaceinkorea.epik_boot3_api.admin.auth.controller;

import com.everyplaceinkorea.epik_boot3_api.admin.auth.dto.AdminUserDto;
import com.everyplaceinkorea.epik_boot3_api.admin.auth.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

  private final AdminUserService adminUserService;

  @GetMapping("/current")
  public ResponseEntity<AdminUserDto> getCurrentAdmin(Authentication authentication) {

    return ResponseEntity.ok(adminUserService.getCurrentAdminUser(authentication));
  }
}
