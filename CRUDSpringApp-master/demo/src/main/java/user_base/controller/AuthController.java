package user_base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user_base.domain.request.SigninRequest;
import user_base.domain.request.SignupRequest;
import user_base.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignupRequest request){
        authService.registerUser(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("signin")
    public ResponseEntity loginUser(@Valid @RequestBody SigninRequest request){
        String token = authService.loginUser(request);
        return new ResponseEntity(token, HttpStatus.OK);
    }
}
