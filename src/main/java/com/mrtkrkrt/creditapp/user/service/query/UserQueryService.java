package com.mrtkrkrt.creditapp.user.service.query;

import com.mrtkrkrt.creditapp.common.exception.ErrorCode;
import com.mrtkrkrt.creditapp.common.exception.GenericException;
import com.mrtkrkrt.creditapp.user.dto.query.RetrieveUserResponse;
import com.mrtkrkrt.creditapp.user.model.UserElastic;
import com.mrtkrkrt.creditapp.user.repository.UserElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserQueryService {

    private final UserElasticRepository userElasticRepository;

    public RetrieveUserResponse getUserByTckn(String tckn) {
        log.info("UserQueryService -> getUser is started with userr tckn: {}", tckn);
        UserElastic user = userElasticRepository.findByTckn(tckn).orElseThrow(() -> GenericException.builder()
                .message(ErrorCode.USER_NOT_FOUND)
                .logMessage("User not found with tckn: " + tckn)
                .build());
        return RetrieveUserResponse.builder().user(user).build();
    }
}
