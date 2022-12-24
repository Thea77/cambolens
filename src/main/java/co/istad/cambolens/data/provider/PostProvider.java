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
                AND();
                WHERE("p.title ILIKE '%' || #{post.title} || '%'", "p.is_enabled = TRUE", "u.is_enabled = TRUE");
                OR();
                WHERE("p.author ILIKE '%' || #{post.author} || '%'", "p.is_enabled = TRUE", "u.is_enabled = TRUE");
            }
            ORDER_BY("p.id DESC");

        }}.toString();
    }

    public String buildSelectTopDownloadSql(@Param("post")Post post) {
        return new SQL() {{
            SELECT("*");
            FROM("posts p");
            INNER_JOIN("users u ON p.author = u.id");
            INNER_JOIN("images i ON p.photo = i.id");
            WHERE("u.is_enabled = TRUE");
            if (post != null) {
                AND();
                WHERE("p.title ILIKE '%' || #{post.title} || '%'", "p.is_enabled = TRUE", "u.is_enabled = TRUE");
                OR();
                WHERE("p.author ILIKE '%' || #{post.author} || '%'", "p.is_enabled = TRUE", "u.is_enabled = TRUE");
            }
             ORDER_BY("i.download DESC");
            

        }}.toString();
    }
    

    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO("posts");
            VALUES("title", "#{post.title}");
            VALUES("description", "#{post.description}");
            VALUES("photo", "#{post.photo.id}");
            VALUES("author", "#{post.author.id}");
            VALUES("location", "#{post.location}");
            VALUES("date_published", "#{post.datePublished}");
            VALUES("is_enabled", "#{post.isEnabled}");
        }}.toString();
    }

    public String buildUpdateSql(){
        return new SQL(){{
            UPDATE("posts");
            SET("title = #{post.title}");
            SET("description = #{post.description}");
            SET("author = #{post.author.id}");
            SET("photo = #{post.photo.id}");
            SET("date_published = #{post.datePublished}");
            SET("location = #{post.location}");
            SET("is_enabled = #{post.isEnabled}");
            WHERE("id = #{post.id}");
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

    public String buildInsertPostCategorySql(){
        return new SQL(){{
            INSERT_INTO("posts_categories");
            VALUES("post_id", "#{postId}");
            VALUES("category_id", "#{cateId}");          
        }}.toString();
    }
}
