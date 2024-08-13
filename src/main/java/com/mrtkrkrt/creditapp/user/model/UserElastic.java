package com.mrtkrkrt.creditapp.user.model;

import com.mrtkrkrt.creditapp.loan.model.Loan;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

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
    private String password;
    private List<Loan> loans;
    private String tckn;
}
