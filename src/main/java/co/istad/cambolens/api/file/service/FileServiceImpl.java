package co.istad.cambolens.api.file.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.file.dto.FileDto;
import co.istad.cambolens.api.file.mapper.FileMapper;
import co.istad.cambolens.data.repository.FileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
   
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Value("${file.server-path}")
    private String serverPath;

    @Value("${file.uri}")
    private String uri;

    @Override
    public FileDto getFileByID(Long id) {

        Optional<File> opFile = fileRepository.selectByID(id);

        File file = opFile.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "File with ID = " + id + " is not found in DB"));

        FileDto fileDto = fileMapper.fromModel(file);
        String name = fileDto.getUuid() + "." + fileDto.getExtension().trim();
        fileDto.setName(name);
        fileDto.setUri(uri + name);

        return fileDto;
    }

    @Override
    public boolean existsFileID(Long id) {
        return fileRepository.existsFileID(id);
    }


    
}
