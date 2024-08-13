package com.mrtkrkrt.creditapp.user.repository;

import com.mrtkrkrt.creditapp.user.model.UserElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticRepository extends ElasticsearchRepository<UserElastic, Long>{
    UserElastic findByTckn(String tckn);
}
