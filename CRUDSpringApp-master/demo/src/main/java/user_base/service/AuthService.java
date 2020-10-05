package user_base.service;

import user_base.domain.request.SigninRequest;
import user_base.domain.request.SignupRequest;

public interface AuthService {

    void registerUser(SignupRequest request);

    String loginUser(SigninRequest request);
}
