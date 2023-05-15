package com.safar.service;

import com.safar.exception.LoginException;
import com.safar.model.CurrentUserSession;
import com.safar.model.UserLoginDTO;

public interface UserLoginService {
    public CurrentUserSession userLogin(UserLoginDTO userLoginDTO) throws LoginException;
    public String userLogout(String key) throws LoginException;
}
