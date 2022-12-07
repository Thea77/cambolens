package co.istad.cambolens.api.user.service;

import java.util.List;

import co.istad.cambolens.api.user.dto.UserDto;

public interface UserService {
    
        /**
     * Get all user from database
     * @return List<UserDto>
     */
    List<UserDto> getAllUsers();
}
