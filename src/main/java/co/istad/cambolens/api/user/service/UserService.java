package co.istad.cambolens.api.user.service;

import java.util.List;

import co.istad.cambolens.api.user.dto.UserDto;

public interface UserService {
    
        /**
     * Get all user from database
     * @return List<UserDto>
     */
    List<UserDto> getAllUsers();

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


}
