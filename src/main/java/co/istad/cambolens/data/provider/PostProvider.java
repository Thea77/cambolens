package co.istad.cambolens.data.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import co.istad.cambolens.api.post.Post;

public class PostProvider {
    public String buildSelectSql(@Param("post")Post post) {
        return new SQL() {{
            SELECT("*");
            FROM("posts p");
            INNER_JOIN("users u ON p.author = u.id");
            WHERE("u.is_enabled = TRUE");
            if (post != null) {
                OR();
                WHERE("p.title ILIKE '%' || #{post.title} || '%'", "p.is_enabled = TRUE", "u.is_enabled = TRUE");
                OR();
                WHERE("p.author ILIKE '%' || #{post.author} || '%'", "p.is_enabled = TRUE", "u.is_enabled = TRUE");
            }
            ORDER_BY("p.id DESC");

        }}.toString();
    }

    public String buildSelectPostPhotoSql() {
        return new SQL() {{
            SELECT("*");
            FROM("images AS i");
            WHERE("i.id = #{id}");
        }}.toString();
    }

     public String buildSelectPostCategoriesByIdSql() {
        return new SQL() {{
            SELECT("*");
            FROM("categories c");
            INNER_JOIN("posts_categories pc ON pc.category_id = c.id");
            WHERE("pc.post_id = #{cateId}");
        }}.toString();
    }

    public String buildSelectPostAuthorSql() {
        return new SQL() {{
            SELECT("id,username,email,phone_number,family_name,given_name,is_enabled");
            FROM("users");
            WHERE("id = #{id}");
        }}.toString();
    }
}
