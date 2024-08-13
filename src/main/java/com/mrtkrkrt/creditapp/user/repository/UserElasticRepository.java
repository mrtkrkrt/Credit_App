package com.mrtkrkrt.creditapp.user.repository;

import com.mrtkrkrt.creditapp.user.model.UserElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface UserElasticRepository extends ElasticsearchRepository<UserElastic, Long>{
    Optional<UserElastic> findByTckn(String tckn);
}
