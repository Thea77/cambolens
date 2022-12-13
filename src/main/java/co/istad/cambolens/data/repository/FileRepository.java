package co.istad.cambolens.data.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.data.provider.FileProvider;

@Repository
public interface FileRepository {

    @SelectProvider(type = FileProvider.class, method = "buildSelectFileByIDSql")
    @Results(id = "fileResultMap", value = {
            @Result(column = "is_enabled", property = "isEnabled")
    })
    Optional<File> selectByID(@Param("id") Long id);

    @Select("SELECT EXISTS(SELECT * FROM images WHERE id = #{id})")
    boolean existsFileID(@Param("id") Long id);
}
