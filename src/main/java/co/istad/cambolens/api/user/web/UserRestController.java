package co.istad.cambolens.api.user.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.auth.web.RegisterDto;
import co.istad.cambolens.api.user.User;
import co.istad.cambolens.api.user.dto.IsEnabledDto;
import co.istad.cambolens.api.user.dto.UserDto;
import co.istad.cambolens.api.user.dto.UserEditProfile;
import co.istad.cambolens.api.user.mapper.UserMapper;
import co.istad.cambolens.api.user.service.UserServiceImpl;
import co.istad.cambolens.config.security.CustomUserSecurity;
import co.istad.cambolens.shared.rest.Rest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;

    
    @Value("${file.uri}")
    private String fileBaseUri;
    
    @GetMapping
    ResponseEntity<?> getUsers(@RequestParam(required = false, defaultValue = "1") int pageNum,
                               @RequestParam(required = false, defaultValue = "20") int pageSize) {

        PageInfo<UserDto> userDtoList = userServiceImpl.getAllUsers(pageNum, pageSize);

        Rest<PageInfo<UserDto>> rest = new Rest<>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Users have been fetched");
        rest.setData(userDtoList);

        return ResponseEntity.ok(rest);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> doGetUserById(@PathVariable("id") Long id) {

        UserDto userDto = userServiceImpl.getUserById(id);

        Rest<Object> rest = Rest.ok()
                            .setData(userDto)
                            .setMessage("User has been fetched successfully.");
        return ResponseEntity.ok(rest);
    }

    @GetMapping("/me")
    ResponseEntity<?> getCurrentUser(Authentication authentication) {

        CustomUserSecurity userSecurity = (CustomUserSecurity) authentication.getPrincipal();

        User user = userSecurity.getUser();
        UserDto userDto = userMapper.fromModel(user);
        userDto.getProfile().buildNameAndUri(fileBaseUri);

        Rest<Object> rest = Rest.ok().setData(userDto).setMessage("Retrieving profile information successfully.");

        return ResponseEntity.ok(rest);
    }

    @PutMapping("/edit-profile")
    ResponseEntity<?> doEditUserProfile(Authentication authentication, @RequestBody UserEditProfile body) {
        
        CustomUserSecurity userSecurity =  (CustomUserSecurity) authentication.getPrincipal();

        User user = userSecurity.getUser();
        

        Rest<Object> rest = Rest.ok()
                        .setData(userServiceImpl.editUserProfile(body))
                        .setMessage("Editing profile information successfully.");
                        System.out.println("nyUser"+ user);

        return ResponseEntity.ok(rest);
    }



    @PostMapping("/create-users")
    public ResponseEntity<?> doCreateUser(@Valid @RequestBody RegisterDto body){
        UserDto userDto = userServiceImpl.createUser(body);
        // log.info("Body={}",body);
        Rest<Object> rest = Rest.ok()
                    .setData(userDto)
                    .setMessage("You have been created a user successfully.");
        return ResponseEntity.ok(rest);
    }

    @PutMapping("/{id}/user-status")
    ResponseEntity<?> enableOrBanUserById(@PathVariable("id") Long id, @RequestBody IsEnabledDto isEnabledDto) {

        UserDto userDto = userServiceImpl.enableAndDisableUser(id, isEnabledDto.getStatus());

        Rest<Object> rest = Rest.ok()
                .setData(userDto)
                .setMessage(isEnabledDto.getStatus() ? String.format("User Id = %d has been enabled.", id) : String.format("User Id = %d has been disabled.", id));

        return ResponseEntity.ok(rest);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {

    UserDto userDto = userServiceImpl.deleteUserById(id);
        Rest<Object> rest = Rest.ok()
                .setData(userDto)
                .setMessage("User has been deleted permanently.");

        return ResponseEntity.ok(rest);
    }


   
}
