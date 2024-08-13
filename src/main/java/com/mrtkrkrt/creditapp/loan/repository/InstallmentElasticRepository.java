package com.mrtkrkrt.creditapp.loan.repository;

import com.mrtkrkrt.creditapp.loan.model.InstallmentElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface InstallmentElasticRepository extends ElasticsearchRepository<InstallmentElastic, Long> {
}
