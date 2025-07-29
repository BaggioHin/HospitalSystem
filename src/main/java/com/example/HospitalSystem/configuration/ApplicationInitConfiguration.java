package com.example.HospitalSystem.configuration;

import com.example.HospitalSystem.constant.PredefinedRole;
import com.example.HospitalSystem.entity.usersAndRole.roles;
import com.example.HospitalSystem.entity.usersAndRole.users;
import com.example.HospitalSystem.repository.RoleRepository;
import com.example.HospitalSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class ApplicationInitConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner() {
        return args -> {
            if(userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(roles.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                roles adminRole = roleRepository.save(roles.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<com.example.HospitalSystem.entity.usersAndRole.roles>();
                roles.add(adminRole);

                users user = users.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
