package co.istad.cambolens.api.auth.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import co.istad.cambolens.api.auth.AuthServiceImpl;
import co.istad.cambolens.shared.rest.Rest;
import co.istad.cambolens.api.user.dto.ProfileDto;
import co.istad.cambolens.api.user.dto.UserDto;
// import co.istad.cambolens.api.user.dto.UserInsertDto;
// import co.istad.cambolens.api.user.service.UserServiceImp;
import co.istad.cambolens.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthServiceImpl authService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto body){

        List<Integer> role = new ArrayList<Integer>();
        role.add(2);
        body.setRoleIds(role); 

        UserDto userDto = authService.register(body);
        // log.info("Body={}",body);
        Rest<Object> rest = Rest.ok()
                    .setData(userDto)
                    .setMessage("You have been registerd successfully.");
        return ResponseEntity.ok(rest);
    }

    

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LogInDto body) throws Exception {
        
        // log.info("Body={}",body);
        AuthDto authDto = authService.logIn(body);

        var rest = new Rest<AuthDto>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("You have logged in successfully.");
        rest.setData(authDto);

        return ResponseEntity.ok(rest);
    }

    @PostMapping("send-email-confirmation")
    ResponseEntity<?> sendEmailConfirmation(@RequestBody EmailConfirmationDto emailConfirmationDto) throws MessagingException, UnsupportedEncodingException, ResponseStatusException {

        authService.sendEmailConfirmation(emailConfirmationDto.getValue());

        var rest = new HashMap<String, Object>();
        rest.put("status", true);
        rest.put("code", HttpStatus.OK.value());
        rest.put("message", "Please check and confirm your email.");
        rest.put("timestamp", DateTimeUtils.getTS());

        return ResponseEntity.ok(rest);
    }

    
    @GetMapping("verify-email")
    String verifyEmail(@RequestParam("email") String email,
                             @RequestParam("verificationCode") String verificationCode) {

        authService.verifyEmail(email, verificationCode);

        return "Your email has been verified..!";
    }


    @PutMapping("/change-password")
    ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {

        authService.changePassword(changePasswordDto);

        var rest = new HashMap<String, Object>();
        rest.put("status", true);
        rest.put("code", HttpStatus.OK.value());
        rest.put("message", "Password has been changed successfully.");
        rest.put("timestamp", DateTimeUtils.getTS());

        return ResponseEntity.ok(rest);
    }

    @PostMapping("/forgot-password")
    ResponseEntity<?> doForgotPassword(@Valid @RequestBody EmailConfirmationDto emailConfirmationDto) throws MessagingException, UnsupportedEncodingException, ResponseStatusException {

        authService.forgotPassword(emailConfirmationDto.getValue());

        var rest = new HashMap<String, Object>();
        rest.put("status", true);
        rest.put("code", HttpStatus.OK.value());
        rest.put("message", "Please confirm your email and reset password.");
        rest.put("timestamp", DateTimeUtils.getTS());

        return ResponseEntity.ok(rest);
    }

    @GetMapping("reset-password")
    String doVerifyForgotPassword(@RequestParam("email") String email,
                        @RequestParam("token") String token) {

        authService.verifyForgotPassword(email, token);

        return "Reset Your Password";
    }

    @PutMapping("/reset-password")
    ResponseEntity<?> resetPassword(@Valid @RequestParam("email") String email,
                    @RequestParam("token") String token, @RequestBody ResetPasswordDto resetPasswordDto) {

        authService.resetPassword(email, token, resetPasswordDto);

        var rest = new HashMap<String, Object>();
        rest.put("status", true);
        rest.put("code", HttpStatus.OK.value());
        rest.put("message", "Password has been reseted successfully.");
        rest.put("timestamp", DateTimeUtils.getTS());

        return ResponseEntity.ok(rest);
    }


    @PutMapping("/change-profile")
    ResponseEntity<?> changeProfile(@Valid @RequestBody ProfileDto profileDto) {

        authService.changeProfile(profileDto);

        var rest = new HashMap<String, Object>();
        rest.put("status", true);
        rest.put("code", HttpStatus.OK.value());
        rest.put("message", "Profile has been changed successfully.");
        rest.put("timestamp", DateTimeUtils.getTS());

        return ResponseEntity.ok(rest);
    }





/**
 *  login and response JWT
 */   
    // @PostMapping("/login")
    // public ResponseEntity<TokenResponse> getToken(@Valid @RequestBody TokenRequest tokenRequest) throws Exception {
        
    //     // check if valid user
    //     authenticate(tokenRequest.getUsername(), tokenRequest.getPassword());

    //     // get user from the database
    //     UserDetails user = userDetailsService.loadUserByUsername(tokenRequest.getUsername());

    //     // create token
    //     String token = jwtTokenUtil.generateToken(user);

    //     return ResponseEntity.ok().body(new TokenResponse(token));
    // }

    // private void authenticate(String username, String password) throws Exception {
    //     try {
    //         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    //     } catch (DisabledException e) {
    //         throw new Exception("USER_DISABLED", e);
    //     } catch (BadCredentialsException e) {
    //         throw new Exception("INVALID_CREDENTIALS", e);
    //     }
    // }


}
