package co.istad.cambolens.api.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import co.istad.cambolens.data.repository.RoleRepository;
import co.istad.cambolens.api.auth.AuthMapper;
import co.istad.cambolens.api.auth.web.RegisterDto;
import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.file.service.FileServiceImpl;
import co.istad.cambolens.api.user.Role;
import co.istad.cambolens.api.user.User;
import co.istad.cambolens.api.user.dto.UserDto;
import co.istad.cambolens.api.user.dto.UserEditProfile;
import co.istad.cambolens.api.user.mapper.UserMapper;
import co.istad.cambolens.config.security.CustomUserSecurity;
import co.istad.cambolens.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileServiceImpl fileServiceImpl;
    private final RoleRepository roleRepository;
    



    @Value("${file.uri}")
    private String fileBaseUri;

    @Override
    public PageInfo<UserDto> getAllUsers(int pageNum, int pageSize) {
       
        Page<User> bookList = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> userRepository.select());
        PageInfo<User> bookListPageInfo = new PageInfo<>(bookList);
        
        PageInfo<UserDto> userDtoListPageInfo = userMapper.fromListModel(bookListPageInfo);

        // userDtos.forEach(userDto -> userDto.getProfile().buildNameAndUri(fileBaseUri));

        return userDtoListPageInfo;
    }
    
    @Override
    public boolean checkUserEmail(String email) {
        return userRepository.existsWhereEmail(email);
    }

    @Override
    public boolean checkUsername(String username) {
        return userRepository.existsWhereUsername(username);
    }

   @Override
   public UserDto getUserById(Long id) {
        User user = userRepository.selectWhereId(id).orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
        UserDto userDto = userMapper.fromModel(user);
        userDto.getProfile().buildNameAndUri(fileBaseUri);
    
        return userDto;
   }

        

    @Override
    public UserDto createUser(RegisterDto body) {
        User user = authMapper.fromRegisterDto(body);
        user.setProfile(new File(body.getProfileId()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setIsEnabled(false);
        userRepository.insert(user);
        
        // body.getRoleIds().forEach(roleId -> {
        //     userRepository.insertUserRole(user.getId(), roleId);
        // });
    
        List<Role> roles = new ArrayList<>();
        for (Integer roleId : body.getRoleIds()) {
            //insert users_roles
            userRepository.insertUserRole(user.getId(), roleId);
            // to response roles obj in user
            roles.add(roleRepository.selectRoleById(roleId));
        }
        user.setRoles(roles);

        UserDto userDto = userMapper.fromModel(user);
        userDto.setRoles(user.getRoles());
        userDto.setProfile(fileServiceImpl.getFileByID(body.getProfileId()));

        return userDto;
    }

    @Override
    public UserEditProfile editUserProfile(UserEditProfile body ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserSecurity customUserSecurity = (CustomUserSecurity) auth.getPrincipal();

        Long userId = customUserSecurity.getUser().getId();

        User user = userMapper.forEditProfileToModel(body);
        userRepository.updateUserProfile(userId, user);

        return body;
    }

    @Override
    public UserDto enableAndDisableUser(Long id, Boolean isEnabled) {

        User user = userRepository.selectUserWhereId(id).orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
        UserDto userDto = userMapper.fromModel(user);
        userRepository.updateIsEnabledWhereId(id, isEnabled);
        
        userDto.getProfile().buildNameAndUri(fileBaseUri);

        return userDto;
    }


    @Override
    public UserDto deleteUserById(Long id) {

        User user = userRepository.selectWhereId(id).orElseThrow(() -> new UsernameNotFoundException("User is not found!"));

        userRepository.deleteWhereId(id);

        UserDto userDto = userMapper.fromModel(user);
        userDto.getProfile().buildNameAndUri(fileBaseUri);

        return userDto;
    }
}
