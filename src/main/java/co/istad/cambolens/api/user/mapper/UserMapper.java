package co.istad.cambolens.api.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.user.User;
import co.istad.cambolens.api.user.dto.UserDto;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    
    PageInfo<UserDto> fromListModel(PageInfo<User> users);

    UserDto fromModel(User user);

    User toModel(UserDto dto);
    


}
