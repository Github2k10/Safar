package com.safar.service;

import com.safar.exception.AdminException;
import com.safar.exception.LoginException;
import com.safar.model.Admin;
import com.safar.model.AdminLoginDTO;
import com.safar.model.CurrentAdminSession;
import com.safar.repository.AdminRepository;
import com.safar.repository.CurrentAdminSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AdminLoginServiceImpl implements AdminLoginService{

    @Autowired
    private CurrentAdminSessionRepository adminSessionRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public CurrentAdminSession adminLogin(AdminLoginDTO loginDTO) throws LoginException, AdminException {
        List<Admin> admins = adminRepository.findByEmail(loginDTO.getEmail());
        
        if(admins.isEmpty()) throw new AdminException("Please enter a valid email!");
        
        Admin registeredAdmin = admins.get(0);
        
        if(registeredAdmin == null) throw new AdminException("Please enter a valid email!");

        Optional<CurrentAdminSession> loggedInAdmin = adminSessionRepository.findById(registeredAdmin.getAdminID());
        if(loggedInAdmin.isPresent()) throw new LoginException("Admin is already loggedIn!");

        if(registeredAdmin.getPassword().equals(loginDTO.getPassword())){
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[10];
            secureRandom.nextBytes(keyBytes);
            
            String key = Base64.getEncoder().encodeToString(keyBytes);
            
            CurrentAdminSession adminSession = new CurrentAdminSession();
            adminSession.setAdminID(registeredAdmin.getAdminID());
            adminSession.setAid(key);
            adminSession.setTime(LocalDateTime.now());
            return adminSessionRepository.save(adminSession);
        }else
            throw new LoginException("Please enter valid password!");
    }

    @Override
    public String adminLogout(String key) throws LoginException {
        CurrentAdminSession currentAdminSession = adminSessionRepository.findByaid(key);
        if(currentAdminSession == null) throw new LoginException("Invalid Admin login key!");
        adminSessionRepository.delete(currentAdminSession);
        return "Admin logged out!";
    }
}
