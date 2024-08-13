package com.mrtkrkrt.creditapp.user.service;

import com.mrtkrkrt.creditapp.user.UserService;
import com.mrtkrkrt.creditapp.user.dto.query.RetrieveUserResponse;
import com.mrtkrkrt.creditapp.user.model.UserElastic;
import com.mrtkrkrt.creditapp.user.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserQueryService userQueryService;

    @Override
    public UserElastic getUserByTckn(String tckn) {
        log.info("UserServiceImpl -> getUserByTckn is started, tckn: {}", tckn);
        RetrieveUserResponse retrieveUserResponse = userQueryService.getUserByTckn(tckn);
        return retrieveUserResponse.getUser();
    }
}
