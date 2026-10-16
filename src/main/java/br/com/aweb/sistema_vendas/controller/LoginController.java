package br.com.aweb.sistema_vendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // O POST /login é tratado pelo Spring Security
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // O POST /logout (dentro de logout.html) é tratado pelo Spring Security
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
