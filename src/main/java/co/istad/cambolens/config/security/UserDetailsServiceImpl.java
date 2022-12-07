package co.istad.cambolens.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.istad.cambolens.api.user.User;
import co.istad.cambolens.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.selectByUserName(username);
       if(user == null){
        throw new UsernameNotFoundException(username);
       }
       CustomUserSecurity userSecurity = new CustomUserSecurity();
       userSecurity.setUser(user);

        log.info("loadUserByUsername= {}", userSecurity);

       return userSecurity;
    }
    
}
