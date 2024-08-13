package com.mrtkrkrt.creditapp.user.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user")
@Builder
@Setter
@Getter
@AllArgsConstructor
public class UserElastic {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String tckn;
}
