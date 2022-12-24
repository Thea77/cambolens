package co.istad.cambolens.data.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import co.istad.cambolens.api.post.Category;

@Repository
public interface CategoryRepository {
    
    @Select("SELECT * FROM categories WHERE id = #{id}")
    @Result(column = "is_enabled" , property = "isEnabled")
    Category selectById(@Param("id") Integer id);
}
