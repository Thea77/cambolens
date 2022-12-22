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

    @SelectProvider(type = PostProvider.class, method = "buildSelectPostAuthorSql")
        @Result(column = "id", property = "id")
        @Result(column = "family_name", property = "familyName")
        @Result(column = "given_name", property = "givenName")
        @Result(column = "phone_number", property = "phoneNumber")
        @Result(column = "is_enabled", property = "isEnabled")
        @Result(column = "reset_token", property = "resetToken")
        // @Result(column = "profile", property = "profile", one = @One(select = "selectUserProfile"))
        // @Result(column = "id", property = "roles", many = @Many(select = "selectUserRoles"))
    User selectPostAuthor(@Param("id") Long id);

    @SelectProvider(type = PostProvider.class, method = "buildSelectPostPhotoSql")
    @Result(column = "is_enabled", property = "isEnabled")
    File selectPostPhoto(@Param("id") Long id);

    @SelectProvider(type = PostProvider.class, method = "buildSelectPostCategoriesByIdSql")
    List<Category> selectPostCategoriesById(@Param("cateId") Long cateId);
}
