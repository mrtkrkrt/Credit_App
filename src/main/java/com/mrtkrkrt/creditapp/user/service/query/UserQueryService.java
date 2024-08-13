package com.mrtkrkrt.creditapp.user.service.query;

import com.mrtkrkrt.creditapp.user.dto.query.RetrieveUserResponse;
import com.mrtkrkrt.creditapp.user.repository.UserElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserQueryService {

    private final UserElasticRepository userElasticRepository;

    public RetrieveUserResponse getUser(String tckn) {
        log.info("UserQueryService -> getUser is started with userr tckn: {}", tckn);
        return RetrieveUserResponse.builder().user(userElasticRepository.findByTckn(tckn)).build();
    }
}
