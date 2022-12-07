package co.istad.cambolens.api.auth.web;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import co.istad.cambolens.api.auth.TokenResponse;
import co.istad.cambolens.api.user.dto.UserDto;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class AuthDto {

    private UserDto user;

    private List<String> roles;

    private String token;

}
