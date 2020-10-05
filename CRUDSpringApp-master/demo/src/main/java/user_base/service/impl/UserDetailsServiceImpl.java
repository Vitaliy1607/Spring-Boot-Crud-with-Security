package user_base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import user_base.Entity.UserDocument;
import user_base.exceptions.NotFoundException;
import user_base.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            UserDocument userDocument = userRepository.findByEmailAddress(email)
                    .orElseThrow(() -> new NotFoundException("User with email [" + email +"] don`t exist") {
                    });
            return User.builder()
                    .username(userDocument.getEmailAddress())
                    .password(userDocument.getPassword())
                    .authorities(getAuthorities(userDocument))
                    .build();
    }

    private List<SimpleGrantedAuthority> getAuthorities(UserDocument userDocument){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userDocument.getRoles().forEach(r ->
            authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getRole())));;
        return authorities;
    }

}
