package hk.gov.hyd.bunting.controller;


import hk.gov.hyd.bunting.model.Login;
import hk.gov.hyd.bunting.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8082", "http://10.23.106.240:8082"})
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Login login) {
        boolean isValid = loginService.validateLogin(login.getLoginId(), login.getPassword());
        Map<String, Object> response = new HashMap<>();
        response.put("success", isValid);
        return response;

    }
}