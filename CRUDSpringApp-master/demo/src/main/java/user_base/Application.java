package user_base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import user_base.Entity.RoleDocument;
import user_base.Entity.UserDocument;
import user_base.config.AppProperties;
import user_base.repository.RoleRepository;
import user_base.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(value = {AppProperties.class})
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0){
            RoleDocument roleAdmin = new RoleDocument();
            roleAdmin.setRole("ADMIN");

            RoleDocument roleUser = new RoleDocument();
            roleUser.setRole("USER");
            roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));
        }

        if (userRepository.count() == 0){
            RoleDocument roleAdmin = roleRepository.findByRoleIgnoreCase("ADMIN").get();

            UserDocument adminEntity = new UserDocument();
            adminEntity.setFirstName("Admin");
            adminEntity.setLastName("admin");
            adminEntity.setEmailAddress("admin@gmail.com");
            adminEntity.setPassword(passwordEncoder.encode("123"));
            adminEntity.setRoles(Arrays.asList(roleAdmin));
            adminEntity.setLocalDateTime(LocalDateTime.now());

            userRepository.save(adminEntity);

        }
    }
}

