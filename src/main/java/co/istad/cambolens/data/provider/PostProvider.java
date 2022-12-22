package co.istad.cambolens.data.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import co.istad.cambolens.api.post.Post;

public class PostProvider {
    public String buildSelectSql(@Param("post")Post post) {
        return new SQL() {{
            SELECT("*");
            FROM("posts");
            if (post != null) {
                WHERE("title ILIKE '%' || #{post.title} || '%'", "is_enabled = TRUE");
                OR();
                WHERE("author ILIKE '%' || #{post.author} || '%'", "is_enabled = TRUE");
            }
            ORDER_BY("id DESC");

        }}.toString();
    }

    public String buildSelectPostPhotoSql() {
        return new SQL() {{
            SELECT("i.id, i.uuid, i.extension, i.size, i.is_enabled");
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
            SELECT("id,username,email,phone_number,family_name,given_name");
            FROM("users");
            WHERE("id = #{id}");
        }}.toString();
    }
}
