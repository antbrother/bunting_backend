package hk.gov.hyd.bunting.service;

import hk.gov.hyd.bunting.mapper.LoginMapper;
import hk.gov.hyd.bunting.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    final private LoginMapper loginMapper;

    @Autowired
    public LoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }


    public boolean validateLogin(String loginId, String password) {
        Login login = loginMapper.findByLoginIdAndPassword(loginId, password);
        return login != null;

    }
}