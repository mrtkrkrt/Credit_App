package com.mrtkrkrt.creditapp.user.api.query;

import com.mrtkrkrt.creditapp.user.dto.query.RetrieveUserResponse;
import com.mrtkrkrt.creditapp.user.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/")
    public ResponseEntity<RetrieveUserResponse> getUser(@RequestHeader(value = "x-user-tckn") String tckn) {
        RetrieveUserResponse response = userQueryService.getUserByTckn(tckn);
        return ResponseEntity.ok(response);
    }
}
