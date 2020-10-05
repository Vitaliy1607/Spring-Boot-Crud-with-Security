package user_base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user_base.Entity.RoleDocument;
import user_base.Entity.UserDocument;
import user_base.config.jwt.JwtTokenProvider;
import user_base.domain.request.SigninRequest;
import user_base.domain.request.SignupRequest;
import user_base.exceptions.AlreadyExistException;
import user_base.exceptions.NotFoundException;
import user_base.repository.RoleRepository;
import user_base.repository.UserRepository;
import user_base.service.AuthService;

import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void registerUser(SignupRequest request) {
        if (userRepository.existsByEmailAddressIgnoreCase(request.getEmailAddress())){
            throw new AlreadyExistException("User with email [" + request.getEmailAddress() + "] exist");
        }

        String password = request.getPassword();

        String encPassword = passwordEncoder.encode(password);;

        UserDocument userDocument = new UserDocument();
        userDocument.setFirstName(request.getFirstName());
        userDocument.setLastName(request.getLastName());
        userDocument.setPassword(encPassword);
        userDocument.setEmailAddress(request.getEmailAddress());

        RoleDocument roleDocument = roleRepository.findByRoleIgnoreCase("USER")
                .orElseThrow(() -> new  NotFoundException("Roles not found"));
        userDocument.setRoles(Arrays.asList(roleDocument));

        userRepository.save(userDocument);
    }

    @Override
    public String loginUser(SigninRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmailAddress(),
                                request.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
