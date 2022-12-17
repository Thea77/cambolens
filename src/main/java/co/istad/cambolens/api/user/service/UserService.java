package co.istad.cambolens.api.user.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.auth.web.RegisterDto;
import co.istad.cambolens.api.user.dto.UserDto;

public interface UserService {
    
        /**
     * Get all user from database
     * @return List<UserDto>
     */
    PageInfo<UserDto> getAllUsers(int pageNum, int pageSize);

      /**
     * Check user by email in database
     * @param email is the unique identifier of user
     * @return true/false
     */
    boolean checkUserEmail(String email);

     /**
     * Check user by username in database
     * @param username is the unique identifier of user
     * @return true/false
     */
    boolean checkUsername(String username);


    UserDto createUser(RegisterDto dto);

}
