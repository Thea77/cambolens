package co.istad.cambolens.data.provider;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    


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
}
