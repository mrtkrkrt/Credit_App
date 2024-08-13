package com.mrtkrkrt.creditapp.user;

import com.mrtkrkrt.creditapp.loan.model.Loan;
import com.mrtkrkrt.creditapp.user.model.User;
import com.mrtkrkrt.creditapp.user.model.UserElastic;

public interface UserService {
    UserElastic getUserByTckn(String tckn);
    User addLoan(String userId, Loan loan);
}
