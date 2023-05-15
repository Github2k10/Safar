package com.safar.service;

import com.safar.exception.AdminException;
import com.safar.exception.LoginException;
import com.safar.model.AdminLoginDTO;
import com.safar.model.CurrentAdminSession;

public interface AdminLoginService {
    public CurrentAdminSession adminLogin(AdminLoginDTO loginDTO) throws LoginException, AdminException;
    public String adminLogout(String key) throws LoginException;
}
