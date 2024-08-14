package com.mrtkrkrt.creditapp.loan.repository;

import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface InstallmentElasticRepository extends ElasticsearchRepository<InstallmentElastic, Long> {
    List<InstallmentElastic> findByLoanId(String loanId);
}
