package co.istad.cambolens.api.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.file.dto.FileDto;

public interface FileService {
    

    PageInfo<FileDto> findAllFiles(int pageNum, int pageSize);

    FileDto uploadOne(MultipartFile file);

    List<FileDto> uploadAll(List<MultipartFile> files);

    FileDto updateFile(MultipartFile file, Long id);

    void deleteFileByUUID(String uuid);

    /**
     * Get a file from database by ID
     * @param id is the unique identifier of file in database
     * @return FileDto
     */
    FileDto getFileByID(Long id);

    FileDto showDownloadCount(String uuid);


    FileDto getFileByUUID(String uuid);


     /**
     * Check file ID in database exist or not
     * @param id is the unique identifier of file in database
     * @return true if exists and false if not exists
     */
    boolean existsFileID(Long id);


    FileDto downloadImage(String uuid);

    FileDto countDownloadImage(String uuid);
}
