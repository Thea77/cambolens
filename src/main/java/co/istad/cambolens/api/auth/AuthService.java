package co.istad.cambolens.api.auth;

import co.istad.cambolens.api.auth.web.AuthDto;
import co.istad.cambolens.api.auth.web.ChangePasswordDto;
import co.istad.cambolens.api.auth.web.LogInDto;
import co.istad.cambolens.api.auth.web.RegisterDto;
import co.istad.cambolens.api.auth.web.ResetPasswordDto;
import co.istad.cambolens.api.user.dto.ProfileDto;
import co.istad.cambolens.api.user.dto.UserDto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthService {

    /**
     * Authenticate user with email or username
     * @param logInDto contains required information for authentication process
     * @return AuthDto
     */
    AuthDto logIn(LogInDto logInDto);



    /**
     * Register new user into database
     * @param registerDto contains required and optional information for register process
     * @return UserDto
     */
    UserDto register(RegisterDto registerDto);



    /**
     * Change password of user
     * @param changePasswordDto contains required credentials
     */
    void changePassword(ChangePasswordDto changePasswordDto);

    void forgotPassword(String email) throws MessagingException, UnsupportedEncodingException;;

    void changeProfile(ProfileDto profileDto);

    void sendEmailConfirmation(String email) throws MessagingException, UnsupportedEncodingException;


    void verifyEmail(String email, String verificationCode);

}
