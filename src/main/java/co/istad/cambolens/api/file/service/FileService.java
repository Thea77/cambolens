package co.istad.cambolens.api.file.service;

import co.istad.cambolens.api.file.dto.FileDto;

public interface FileService {
    
    /**
     * Get a file from database by ID
     * @param id is the unique identifier of file in database
     * @return FileDto
     */
    FileDto getFileByID(Long id);

     /**
     * Check file ID in database exist or not
     * @param id is the unique identifier of file in database
     * @return true if exists and false if not exists
     */
    boolean existsFileID(Long id);
}
