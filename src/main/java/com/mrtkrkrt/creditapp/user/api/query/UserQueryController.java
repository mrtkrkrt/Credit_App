package com.mrtkrkrt.creditapp.user.api.query;

import static com.mrtkrkrt.creditapp.common.constants.CommonHeaderConstants.X_USER_TCKN;

import com.mrtkrkrt.creditapp.user.dto.query.RetrieveUserResponse;
import com.mrtkrkrt.creditapp.user.service.query.UserQueryService;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/")
    public ResponseEntity<RetrieveUserResponse> getUser() {
        RetrieveUserResponse response = userQueryService.getUserByTckn(MDC.get(X_USER_TCKN));
        return ResponseEntity.ok(response);
    }
}
