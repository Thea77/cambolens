package co.istad.cambolens.data.repository;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.post.Category;
import co.istad.cambolens.api.post.Post;
import co.istad.cambolens.api.user.User;
import co.istad.cambolens.data.provider.PostProvider;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Repository
public interface PostRepository {

    @SelectProvider(type = PostProvider.class, method = "buildSelectSql")
    @Results(id = "postResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "date_published", property = "datePublished"),
            @Result(column = "is_enabled", property = "isEnabled"),
            @Result(column = "author", property = "author", one = @One(select = "selectPostAuthor")),
            @Result(column = "photo", property = "photo", one = @One(select = "selectPostPhoto")),
            @Result(column = "id", property = "categories", many = @Many(select = "selectPostCategoriesById"))
    })
    List<Post> select(@Param("post") Post post);

    @Select("SELECT * FROM posts WHERE id = #{id}")
    @ResultMap(value = "postResultMap")
    Post selectById(@Param("id") Long id);

    // @SelectProvider(type = PostProvider.class, method = "buildSelectTopDownloadSql")
    @Select("SELECT * FROM posts p"+
                    " INNER JOIN users u ON p.author = u.id" +
                    " INNER JOIN images i ON p.photo = i.id" +
                    " WHERE u.is_enabled = TRUE" +
                    " AND i.download NOTNULL" +
                    " ORDER BY i.download DESC" +
                    " LIMIT 10 ")
    @ResultMap(value = "postResultMap")
    List<Post> selectTopDownload();
    

/**
     * Insert into posts
     * @param post
     */
    @InsertProvider(type = PostProvider.class, method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id",keyProperty = "id")
    @ResultMap(value = "postResultMap")
    void insert(@Param("post") Post post);

/**
     * Update  posts
     * @param post
     */
    @UpdateProvider(type = PostProvider.class, method = "buildUpdateSql")
    @ResultMap(value = "postResultMap")
    void update(@Param("post") Post post);


    @SelectProvider(type = PostProvider.class, method = "buildSelectPostAuthorSql")
        @Result(column = "id", property = "id")
        @Result(column = "family_name", property = "familyName")
        @Result(column = "given_name", property = "givenName")
        @Result(column = "phone_number", property = "phoneNumber")
        @Result(column = "is_enabled", property = "isEnabled")
        @Result(column = "profile", property = "profile", one = @One(select = "selectPostPhoto"))
    User selectPostAuthor(@Param("id") Long id);

    @SelectProvider(type = PostProvider.class, method = "buildSelectPostPhotoSql")
    @Result(column = "is_enabled", property = "isEnabled")
    File selectPostPhoto(@Param("id") Long id);

    @SelectProvider(type = PostProvider.class, method = "buildSelectPostCategoriesByIdSql")
    List<Category> selectPostCategoriesById(@Param("cateId") Long cateId);

    @InsertProvider(type = PostProvider.class, method = "buildInsertPostCategorySql")
    // @ResultMap(value = "bookResultMapper")
    void insertPostCategory(@Param("postId") Long postId, @Param("cateId") Integer cateId);

    @Delete("DELETE FROM posts_categories WHERE post_id = #{postId}")
    void deletePostCategory(@Param("postId") Long postId);

    @Select("SELECT EXISTS (SELECT * FROM posts WHERE id = #{id})")
    boolean existsById(@Param("id") Long id);

    @Delete("DELETE FROM posts WHERE id = #{postId}")
    void deletePost(@Param("postId") Long postId);
}
