package co.istad.cambolens.api.auth;

import co.istad.cambolens.api.auth.web.AuthDto;
import co.istad.cambolens.api.auth.web.LogInDto;
import co.istad.cambolens.api.auth.web.RegisterDto;
import co.istad.cambolens.api.user.dto.UserDto;
import co.istad.cambolens.api.user.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User fromRegisterDto(RegisterDto registerDto);
    User fromLoginDto(LogInDto logInDto);

    AuthDto toAuthDto(UserDto userDto);

}
