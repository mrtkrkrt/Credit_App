package com.mrtkrkrt.creditapp.loan.service.query;

import com.mrtkrkrt.creditapp.loan.dto.query.RetrieveInstallmentResponse;
import com.mrtkrkrt.creditapp.loan.dto.command.RetrieveInstallmentsServiceCommand;
import com.mrtkrkrt.creditapp.loan.repository.InstallmentElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentQueryService {

    private final InstallmentElasticRepository installmentElasticRepository;

    public RetrieveInstallmentResponse retrieveAllInstallments(RetrieveInstallmentsServiceCommand build) {
        return RetrieveInstallmentResponse.builder().installments(installmentElasticRepository.findByLoanId(build.getLoanId())).build();
    }
}










