package co.istad.cambolens.data.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import co.istad.cambolens.api.user.Role;

@Repository
public interface RoleRepository {
    
    @Select("SELECT * FROM roles WHERE id = #{id}")
    Role selectRoleById(@Param("id") Integer id);
}
