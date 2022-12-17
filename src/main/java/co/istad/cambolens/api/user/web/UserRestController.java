package co.istad.cambolens.api.user.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.auth.web.RegisterDto;
import co.istad.cambolens.api.user.dto.UserDto;
import co.istad.cambolens.api.user.mapper.UserMapper;
import co.istad.cambolens.api.user.service.UserServiceImpl;
import co.istad.cambolens.shared.rest.Rest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    ResponseEntity<?> getBooks(@RequestParam(required = false, defaultValue = "1") int pageNum,
                               @RequestParam(required = false, defaultValue = "20") int pageSize) {

        PageInfo<UserDto> userDtoList = userServiceImpl.getAllUsers(pageNum, pageSize);

        Rest<PageInfo<UserDto>> rest = new Rest<>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Users have been fetched");
        rest.setData(userDtoList);

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

}
