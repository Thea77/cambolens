package co.istad.cambolens.api.user.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.auth.web.RegisterDto;
import co.istad.cambolens.api.user.dto.UserDto;
import co.istad.cambolens.api.user.dto.UserEditProfile;

public interface UserService {
    
        /**
     * Get all user from database
     * @return List<UserDto>
     */
    PageInfo<UserDto> getAllUsers(int pageNum, int pageSize);

    
    /**
     * Get user information from database by username or email
     * @param usernameOrEmail can be username or email which is the unique identifier of user
     * @return UserDto
     */
    UserDto getUserById(Long id);


    UserEditProfile editUserProfile(UserEditProfile editProfile);

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

     /**
     * Enable or Disable user by ID
     * @param id is the unique identifier of user
     * @param isEnabled is the status will be updating
     */
    UserDto enableAndDisableUser(Long id, Boolean isEnabled);


    UserDto deleteUserById(Long id);
}
