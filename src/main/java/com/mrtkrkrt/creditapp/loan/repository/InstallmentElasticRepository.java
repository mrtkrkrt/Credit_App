package com.mrtkrkrt.creditapp.loan.repository;

import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import com.mrtkrkrt.creditapp.loan.model.enums.InstallmentStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InstallmentElasticRepository extends ElasticsearchRepository<InstallmentElastic, Long> {
    List<InstallmentElastic> findByLoanId(String loanId);

    // date less than now jpa query
    List<InstallmentElastic> findByDueDateLessThanEqualAndStatusIsNot(LocalDateTime now, InstallmentStatus installmentStatus);
}
