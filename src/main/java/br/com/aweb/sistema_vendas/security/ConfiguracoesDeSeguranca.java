package br.com.aweb.sistema_vendas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {

    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // públicas
                .requestMatchers("/login", "/css/**", "/js/**", "/error", "/error/**").permitAll()
                // página de logout: qualquer usuário autenticado
                .requestMatchers("/logout").authenticated()
                // regras por perfil: das mais específicas para as mais genéricas
                .requestMatchers("/pedidos/**").hasAnyRole("ADMIN", "PADRAO")
                .requestMatchers("/produtos/**").hasRole("ADMIN")
                .requestMatchers("/clientes/**").hasRole("ADMIN")
                // qualquer outra rota exige login
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/pedidos", true)
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
