package com.sz.contrller;

import com.sz.security.RequiresRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminDashboard {
    @GetMapping("/dashboard")
    //要求用户必须具有ROLE_ADMIN角色才能访问此方法
    @RequiresRole(role = "ROLE_ADMIN")
    public String adminDashboard() {
        return "Welcome to Admin Dashboard!";
    }
}
