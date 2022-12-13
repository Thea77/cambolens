package co.istad.cambolens.data.provider;

import org.apache.ibatis.jdbc.SQL;

public class FileProvider {
    public String buildSelectFileByIDSql() {
        return new SQL() {{
            SELECT("*");
            FROM("images");
            WHERE("id = #{id}", "is_enabled = TRUE");
        }}.toString();
    }
}
