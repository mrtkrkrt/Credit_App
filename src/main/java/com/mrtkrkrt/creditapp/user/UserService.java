package com.mrtkrkrt.creditapp.user;

import com.mrtkrkrt.creditapp.user.model.UserElastic;

public interface UserService {
    UserElastic getUserByTckn(String tckn);
}
