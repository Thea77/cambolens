package co.istad.cambolens.data.provider;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    

    public String buildInsertUserRoleSql() {
        return new SQL() {{
            INSERT_INTO("users_roles");
            VALUES("user_id", "#{userId}");
            VALUES("role_id", "#{roleId}");
        }}.toString();
    }

    public String buildInsertSql() {
        return new SQL() {{
            INSERT_INTO("users");
            VALUES("username", "#{user.username}");
            VALUES("email", "#{user.email}");
            VALUES("family_name", "#{user.familyName}");
            VALUES("given_name", "#{user.givenName}");
            VALUES("phone_number", "#{user.phoneNumber}");
            VALUES("profile", "#{user.profile.id}");
            VALUES("is_enabled", "#{user.isEnabled}");
            VALUES("password", "#{user.password}");
        }}.toString();
    }

    public String buildSelectUserByUserName() {
        return new SQL() {
            {
                SELECT("*");
                FROM("users");
                WHERE("username = #{username}", "is_enabled = TRUE");
                ORDER_BY("id DESC");
            }
        }.toString();
    }


    public String buildSelectUserProfileSql() {
        return new SQL() {{
            SELECT("i.id, i.uuid, i.extension, i.size, i.is_enabled");
            FROM("images AS i");
            WHERE("i.id = #{id}");
        }}.toString();
    }


    public String buildSelectUserRolesSql() {
        return new SQL() {{
            SELECT("r.id, r.name");
            FROM("roles AS r");
            INNER_JOIN("users_roles AS ur ON ur.role_id = r.id");
            INNER_JOIN("users AS u ON u.id = ur.user_id");
            WHERE("u.id = #{id}");
        }}.toString();
    }

    public String buildSelectByUsernameOrEmailSql() {
        return new SQL() {{
            SELECT("*");
            FROM("users");
            WHERE("username = #{usernameOrEmail}", "is_enabled = #{isEnabled}");
            OR();
            WHERE("email = #{usernameOrEmail}", "is_enabled = #{isEnabled}");
        }}.toString();
    }

    public String buildEditUserProfileSQL(){
        return new SQL(){{
            UPDATE("users");
            SET("family_name = #{user.familyName}");
            SET("given_name = #{user.givenName}");
            SET("phone_number = #{user.phoneNumber}");
            SET("email = #{user.email}");
            WHERE("id = #{id}");
        }}.toString();
    }
    
    public String buildUpdatePasswordWhereIdSql() {
        return new SQL() {{
            UPDATE("users");
            SET("password = #{encodedPassword}");
            WHERE("id = #{id}");
        }}.toString();
    }


    public String buildUpdateCoverByIdSql() {
        return new SQL() {
            {
                UPDATE("users");
                SET("profile = #{profileId}");
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
