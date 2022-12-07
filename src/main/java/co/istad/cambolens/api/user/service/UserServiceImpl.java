package co.istad.cambolens.api.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.istad.cambolens.api.user.User;
import co.istad.cambolens.api.user.dto.UserDto;
import co.istad.cambolens.api.user.mapper.UserMapper;
import co.istad.cambolens.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    // @Value("${file.uri}")
    // private String fileBaseUri;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.select();
        List<UserDto> userDtos = userMapper.fromListModel(users);
        // userDtos.forEach(userDto -> userDto.getProfile().buildNameAndUri(fileBaseUri));

        return userDtos;
    }
    
}
