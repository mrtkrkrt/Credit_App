package com.mrtkrkrt.creditapp.loan.service.query;

import com.mrtkrkrt.creditapp.loan.dto.LoanDto;
import com.mrtkrkrt.creditapp.loan.dto.command.FilterLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.RetrieveInstallmentResponse;
import com.mrtkrkrt.creditapp.loan.dto.command.RetrieveInstallmentsServiceCommand;
import com.mrtkrkrt.creditapp.loan.dto.query.RetrieveLoanResponse;
import com.mrtkrkrt.creditapp.loan.dto.command.RetrieveLoanServiceCommand;
import com.mrtkrkrt.creditapp.loan.model.LoanElastic;
import com.mrtkrkrt.creditapp.loan.repository.LoanElasticRepository;
import com.mrtkrkrt.creditapp.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanQueryService {

    private final LoanElasticRepository loanElasticRepository;
    private final InstallmentQueryService installmentQueryService;
    private final UserService userService;

    public RetrieveLoanResponse retrieveAllLoans(RetrieveLoanServiceCommand retrieveLoanServiceRequest) {
        List<LoanDto> loanDtos = new ArrayList<>();
        List<LoanElastic> loans = loanElasticRepository.findByUserId(userService.getUserByTckn(retrieveLoanServiceRequest.getTckn()).getId());
        insertInstallments(loans, loanDtos);
        return RetrieveLoanResponse.builder().loans(loanDtos).build();
    }

    private void insertInstallments(List<LoanElastic> loans, List<LoanDto> loanDtos) {
        for (LoanElastic loanElastic : loans) {
            RetrieveInstallmentResponse retrieveInstallmentResponse = installmentQueryService.retrieveAllInstallments(RetrieveInstallmentsServiceCommand.builder().loanId(loanElastic.getId().toString()).build());
            loanDtos.add(LoanDto.builder()
                    .creditId(loanElastic.getId())
                    .installments(retrieveInstallmentResponse.getInstallments())
                    .status(loanElastic.getStatus())
                    .installmentCount(retrieveInstallmentResponse.getInstallments().size())
                    .totalAmount(loanElastic.getAmount())
                    .build());
        }
    }

    public RetrieveLoanResponse filterLoans(FilterLoanServiceCommand filterLoanServiceCommand) {
        List<LoanDto> loanDtos = new ArrayList<>();
        List<LoanElastic> loans = loanElasticRepository.findByUserIdAndStatus(userService.getUserByTckn(filterLoanServiceCommand.getTckn()).getId(), filterLoanServiceCommand.getStatus());
        insertInstallments(loans, loanDtos);
        return RetrieveLoanResponse.builder().loans(loanDtos).build();
    }
}
