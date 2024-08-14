package com.mrtkrkrt.creditapp.loan.repository;

import com.mrtkrkrt.creditapp.loan.model.LoanElastic;
import com.mrtkrkrt.creditapp.loan.model.enums.LoanStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LoanElasticRepository extends ElasticsearchRepository<LoanElastic, Long> {
    List<LoanElastic> findByUserId(Long id);

    List<LoanElastic> findByUserIdAndStatus(Long id, LoanStatus status);
}
