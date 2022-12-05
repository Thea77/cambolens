package co.istad.cambolens.api.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.istad.cambolens.api.user.User;
import co.istad.cambolens.api.user.dto.UserDto;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    
    List<UserDto> fromListModel(List<User> users);

    UserDto fromModel(User user);


}
