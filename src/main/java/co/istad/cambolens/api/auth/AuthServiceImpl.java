package co.istad.cambolens.api.auth;

import co.istad.cambolens.api.auth.web.AuthDto;
import co.istad.cambolens.api.auth.web.ChangePasswordDto;
import co.istad.cambolens.api.auth.web.LogInDto;
import co.istad.cambolens.api.auth.web.RegisterDto;
// import co.istad.cambolens.api.email.EmailServiceImpl;
// import co.istad.cambolens.api.email.web.EmailDto;
import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.file.service.FileServiceImpl;
// import co.istad.cambolens.api.file.service.ImageServiceImpl;
import co.istad.cambolens.api.user.dto.UserDto;
import co.istad.cambolens.api.user.mapper.UserMapper;
import co.istad.cambolens.api.user.User;
import co.istad.cambolens.config.security.CustomUserSecurity;
import co.istad.cambolens.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import co.istad.cambolens.api.auth.TokenRequest;
import co.istad.cambolens.api.auth.TokenResponse;
import co.istad.cambolens.api.auth.jwt.JwtTokenUtil;

import org.springframework.security.authentication.AuthenticationManager;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final FileServiceImpl fileServiceImpl;
    // private final EmailServiceImpl emailService;
    
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Value("${file.uri}")
    private String fileBaseUri;

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;

    // @Override
    // public void changePassword(ChangePasswordDto changePasswordDto) {

    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     CustomUserSecurity customUserSecurity = (CustomUserSecurity) auth.getPrincipal();

    //     if (!bCryptPasswordEncoder.matches(changePasswordDto.getCurrentPassword(), customUserSecurity.getPassword())) {
    //         String reason = "Change password is failed!";
    //         Throwable cause = new Throwable("Current password is wrong!");
    //         throw new BadCredentialsException(reason, cause);
    //     }

    //     Integer userId = customUserSecurity.getUser().getId();

    //     String encodedPassword = bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword());

    //     userRepository.updatePasswordWhereId(userId, encodedPassword);

    // }

    @Override
    public AuthDto logIn(LogInDto logInDto) {

        // System.out.println("Login = " + logInDto);

        // Get user data
        Authentication authentication = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(logInDto.getUsername(), logInDto.getPassword()));
        CustomUserSecurity customUserSecurity = (CustomUserSecurity) authentication.getPrincipal();
   

        UserDto userDto = userMapper.fromModel(customUserSecurity.getUser());
        // System.out.println("myUser"+userDto);
        
        userDto.getProfile().buildNameAndUri(fileBaseUri);

        // generated Toekn
        String myToken = this.buildAuthorizationHeader(logInDto);
        // System.out.println("myToken="+ myToken);

        // // Setup role for response with authDto
        List<String> roles = new ArrayList<>();
        customUserSecurity.getAuthorities().forEach(role -> roles.add(role.getAuthority()));

        return AuthDto.builder()
                .user(userDto)
                .roles(roles)
                .token(myToken)
                .build();
    }



    /**
     * Generate token JWT
     * @param logInDto
     * @return
     */
     private String buildAuthorizationHeader(LogInDto logInDto) {
        
     // check if valid user
        try {
            authenticate(logInDto.getUsername(), logInDto.getPassword());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // get user from the database
        UserDetails user = userDetailsService.loadUserByUsername(logInDto.getUsername());

        // create token
        String token = jwtTokenUtil.generateToken(user);

        // System.out.println("Token="+ token);
        return String.format(token);
    }
      

        /**
         * check Username Password Authentication Token
         * @param username
         * @param password
         * @throws Exception
         */
 private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }




    @Override
    public UserDto register(RegisterDto registerDto) {

        User user = authMapper.fromRegisterDto(registerDto);
        user.setProfile(new File(registerDto.getProfileId()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setIsEnabled(false);
        registerDto.setRoleIds(2); //role EDITOR
        userRepository.insert(user);

        // registerDto.getRoleIds().forEach(roleId -> {
        //     userRepository.insertUserRole(user.getId(), roleId);
            
        // });
        userRepository.insertUserRole(user.getId(), registerDto.getRoleIds());

        UserDto userDto = userMapper.fromModel(user);
        userDto.setProfile(fileServiceImpl.getFileByID(registerDto.getProfileId()));

        return userDto;
    }



    // @Override
    // public void sendEmailConfirmation(String email) throws MessagingException, UnsupportedEncodingException, ResponseStatusException {

    //     var random = new Random();
    //     String code = String.format("%6d", random.nextInt(999999));

    //     var user = userRepository.selectWhereUsernameOrEmail(email, false).orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
    //     user.setVerificationCode(code);

    //     userRepository.updateVerificationCodeWhereId(user.getId(), user.getVerificationCode());

    //     EmailDto<?> emailDto = EmailDto.builder()
    //             .receiver(email)
    //             .subject("Email Verification")
    //             .templateName("email/email-confirmation")
    //             .additionalInfo(user)
    //             .build();

    //     emailService.sendEmail(emailDto);
    // }


    // @Override
    // public void verifyEmail(String email, String verificationCode) {

    //     User user = userRepository.selectWhereEmailAndVerificationCode(email, verificationCode)
    //             .orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
        
    //     userRepository.updateVerificationCodeWhereId(user.getId(), null);
    //     userRepository.updateIsEnabledWhereId(user.getId(), true);
    // }

}
