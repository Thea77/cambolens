package co.istad.cambolens.data.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.user.Role;
import co.istad.cambolens.api.user.User;
import co.istad.cambolens.data.provider.UserProvider;

@Repository
public interface UserRepository {
    
    @Select("SELECT * FROM users WHERE is_enabled = TRUE ORDER BY id DESC")
    @Results(id = "userResultMap", value = {
        @Result(column = "id", property = "id"),
        @Result(column = "family_name", property = "familyName"),
        @Result(column = "given_name", property = "givenName"),
        @Result(column = "phone_number", property = "phoneNumber"),
        @Result(column = "is_enabled", property = "isEnabled"),
        @Result(column = "reset_token", property = "resetToken"),
        @Result(column = "profile", property = "profile", one = @One(select = "selectUserProfile")),
        @Result(column = "id", property = "roles", many = @Many(select = "selectUserRoles"))
})
    List<User> select();

    @InsertProvider(type = UserProvider.class, method = "buildInsertUserRoleSql")
    void insertUserRole(@Param("userId") Long userId, @Param("roleId") Integer roleId);


    @InsertProvider(type = UserProvider.class, method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(@Param("user") User user);

    
    @SelectProvider(type = UserProvider.class ,method = "buildSelectUserByUserName")
    @ResultMap("userResultMap")
    User selectByUserName(@Param("username") String username);


    @SelectProvider(type = UserProvider.class, method = "buildSelectUserProfileSql")
    @Result(column = "is_enabled", property = "isEnabled")
    File selectUserProfile(@Param("id") Long id);

    @SelectProvider(type = UserProvider.class, method = "buildSelectUserRolesSql")
    List<Role> selectUserRoles(@Param("id") Integer id);

    @Select("SELECT EXISTS(SELECT * FROM users WHERE email = #{email})")
    boolean existsWhereEmail(@Param("email") String email);

    @Select("SELECT EXISTS(SELECT * FROM users WHERE username = #{username})")
    boolean existsWhereUsername(@Param("username") String username);

    @SelectProvider(type = UserProvider.class, method = "buildSelectByUsernameOrEmailSql")
    @ResultMap(value = "userResultMap")
    Optional<User> selectWhereUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail, @Param("isEnabled") Boolean isEnabled);
    

    @UpdateProvider(type = UserProvider.class, method = "buildUpdatePasswordWhereIdSql")
    @ResultMap("userResultMap")
    void updatePasswordWhereId(@Param("id") Long id, @Param("encodedPassword") String encodedPassword);
    

    // email verification
    @Select("SELECT * FROM users WHERE email = #{email} AND verification_code = #{verificationCode}")
    @ResultMap("userResultMap")
    Optional<User> selectWhereEmailAndVerificationCode(@Param("email") String email, @Param("verificationCode") String verificationCode);

    //verify email for reset password
    @Select("SELECT * FROM users WHERE email = #{email} AND reset_token = #{resetToken}")
    @ResultMap("userResultMap")
    Optional<User> selectWhereEmailAndResetToken(@Param("email") String email, @Param("resetToken") String resetToken);

    
    @Update("UPDATE users SET verification_code = #{verificationCode} WHERE id = #{id}")
    void updateVerificationCodeWhereId(@Param("id") Long id, @Param("verificationCode") String verificationCode);
    
    @Update("UPDATE users SET reset_token = #{resetToken} WHERE id = #{id}")
    void updateResetTokenWhereId(@Param("id") Long id, @Param("resetToken") String resetToken);
    
    @Update("UPDATE users SET is_enabled = #{isEnabled} WHERE id = #{id}")
    @ResultMap("userResultMap")
    void updateIsEnabledWhereId(@Param("id") Long id, @Param("isEnabled") Boolean isEnabled);
    

    @UpdateProvider(type = UserProvider.class, method = "buildUpdateCoverByIdSql")
    void updateProfileWhereUserId(@Param("id") Long id, @Param("profileId") Long profileId);


    @Select("SELECT EXISTS (SELECT * FROM users WHERE id = #{id})")
    boolean existsById(@Param("id") Long id);
}
