package co.istad.cambolens.data.provider;

import org.apache.ibatis.jdbc.SQL;

public class FileProvider {

    public String buildSelectImageSQL(){
        return new SQL(){{
            SELECT("*");
            FROM("images");
            WHERE("is_enabled = TRUE");
            ORDER_BY("id DESC");
        }}.toString();
    }


    public String buildSelectFileByIDSql() {
        return new SQL() {{
            SELECT("*");
            FROM("images");
            WHERE("id = #{id}", "is_enabled = TRUE");
        }}.toString();
    }

    public String buildInsertImageSQL(){
        return new SQL() {{
            INSERT_INTO("images");
            VALUES("uuid","#{file.uuid}");
            VALUES("extension","#{file.extension}");
            VALUES("size","#{file.size}");
            VALUES("is_enabled","#{file.isEnabled}");
        }}.toString();    
    }

    public String buildUpdateSQL(){
        return new SQL(){{
            UPDATE("images");
            SET("uuid = #{file.uuid}");
            SET("extension = #{file.extension}");
            SET("size = #{file.size}");
            SET("is_enabled = #{file.isEnabled}");
            WHERE("id = #{file.id}");
        }}.toString();
    }

    public String buildSelectFileByUUIDSql() {
        return new SQL() {{
            SELECT("*");
            FROM("images");
            WHERE("uuid = #{uuid}", "is_enabled = TRUE");
        }}.toString();
    }


    public String buildDeleteByUUIDSql() {
        return new SQL() {{
            DELETE_FROM("images");
            WHERE("uuid = #{uuid}");
        }}.toString();
    }

    // public String buildUdpateDownloadCountSQL(){
    //     return new SQL(){{
    //         UPDATE("images");
    //         SET("download = (#{file.download})+1");
    //         WHERE("uuid = #{uuid}");
    //     }}.toString();
    // } 
   
  
}
