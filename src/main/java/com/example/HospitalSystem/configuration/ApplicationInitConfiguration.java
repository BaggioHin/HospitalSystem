package com.example.HospitalSystem.configuration;

import com.example.HospitalSystem.constant.PredefinedRole;
import com.example.HospitalSystem.entity.usersAndRole.Roles;
import com.example.HospitalSystem.entity.usersAndRole.Users;
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
                roleRepository.save(Roles.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                Roles adminRole = roleRepository.save(Roles.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Roles>();
                roles.add(adminRole);

                Users user = Users.builder()
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
