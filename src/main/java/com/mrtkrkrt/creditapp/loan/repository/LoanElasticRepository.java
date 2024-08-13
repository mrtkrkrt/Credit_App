package com.mrtkrkrt.creditapp.loan.repository;

import com.mrtkrkrt.creditapp.loan.model.LoanElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LoanElasticRepository extends ElasticsearchRepository<LoanElastic, Long> {
}
