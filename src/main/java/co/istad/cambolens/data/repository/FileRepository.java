package co.istad.cambolens.data.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.data.provider.FileProvider;

@Repository
public interface FileRepository {


    /**
     * select all Images
     * @return
     */
    @SelectProvider(type = FileProvider.class, method = "buildSelectImageSQL")
    @Results(id= "imageResultMapper", value = {
        @Result(column = "is_enabled",property = "isEnabled")
    })
    List<File> select();


    @SelectProvider(type = FileProvider.class, method = "buildSelectFileByIDSql")
    @Results(id = "fileResultMap", value = {
            @Result(column = "is_enabled", property = "isEnabled")
    })
    Optional<File> selectByID(@Param("id") Long id);


    @SelectProvider(type = FileProvider.class, method = "buildSelectFileByUUIDSql")
    @ResultMap("fileResultMap")
    Optional<File> selectByUUID(@Param("uuid") String uuid);

    @DeleteProvider(type = FileProvider.class, method = "buildDeleteByUUIDSql")
    void deleteByUUID(@Param("uuid") String uuid);
    

    @Select("SELECT EXISTS(SELECT * FROM images WHERE id = #{id})")
    boolean existsFileID(@Param("id") Long id);

      /**
     * insert Image
     * @param image
     */
    @InsertProvider(type = FileProvider.class, method = "buildInsertImageSQL")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(@Param("file") File file);

      /**
     * update
     * @param image
     */
    @UpdateProvider(type = FileProvider.class, method = "buildUpdateSQL")
    void update(@Param("file") File file);


    @Update("UPDATE images set download = #{download}+1 where uuid =#{uuid}")
    void udpateDownloadCount(@Param("download") Integer download, @Param("uuid") String uuid);

    @Select("SELECT * FROM images WHERE uuid =#{uuid}")
    @ResultMap("fileResultMap")
    File selectDownloadCount(@Param("uuid") String uuid);
    
}
