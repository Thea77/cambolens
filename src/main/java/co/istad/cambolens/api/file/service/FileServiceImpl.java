package co.istad.cambolens.api.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.file.dto.FileDto;
import co.istad.cambolens.api.file.mapper.FileMapper;
import co.istad.cambolens.data.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Value("${file.server-path}")
    private String serverPath;

    @Value("${file.uri}")
    private String uri;

    @Override
    public PageInfo<FileDto> findAllFiles(int pageNum, int pageSize) {

        Page<File> fileList = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> fileRepository.select());
        PageInfo<File> fileListPageInfo = new PageInfo<>(fileList);

        PageInfo<FileDto> fileDtoListPageInfo = fileMapper.fromModelList(fileListPageInfo);

        fileDtoListPageInfo.getList().forEach(fileDto -> {
            String name = fileDto.getUuid() + "." + fileDto.getExtension();
            fileDto.setName(name);
            fileDto.setUri(uri + name);
        });

        return fileDtoListPageInfo;

    }

    @Override
    public FileDto getFileByUUID(String uuid) {

        Optional<File> opImgae = fileRepository.selectByUUID(uuid);

        File image = opImgae.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "File with UUID = " + uuid + " is not found in DB"));

        FileDto imageDto = fileMapper.fromModel(image);
        String name = imageDto.getUuid() + "." + imageDto.getExtension();
        imageDto.setName(name);
        imageDto.setUri(uri + name);

        return imageDto;
    }

    @Override
    public FileDto uploadOne(MultipartFile file) {
        return this.save(file);
    }

    @Override
    public List<FileDto> uploadAll(List<MultipartFile> files) {
        List<FileDto> fileDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            fileDtos.add(this.save(file));
        }
        return fileDtos;
    }

    @Override
    public FileDto updateFile(MultipartFile file, Long id) {
        Optional<File> opFile = fileRepository.selectByID(id);
        File image = opFile.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "File with ID = " + id + " is not found in DB"));

        FileDto fileDto = fileMapper.fromModel(image);
        fileDto.setExtension(fileDto.getExtension().trim());
        fileDto.setName(image.getUuid() + "." + image.getExtension().trim());
        fileDto.setUri(uri + fileDto.getName());

        // remove imgae from sever path
        try {
            Path path = Paths.get(serverPath + fileDto.getUuid() + "." + fileDto.getExtension());
            Files.delete(path);
            // removFile = new File(serverPath + fileDto.getName());
            // removFile.delete();
        } catch (Exception e) {
            System.err.println(e);
        }

        return this.update(file, id);
    }

    @Override
    public FileDto getFileByID(Long id) {

        Optional<File> opFile = fileRepository.selectByID(id);

        File file = opFile.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "File with ID = " + id + " is not found in DB"));

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

    /**
     * Save File
     * 
     * @param file
     * @return
     */
    private FileDto save(MultipartFile file) {
        String randomUUID = UUID.randomUUID().toString();
        String extension = "";

        // randomUUID + extension = fileName
        String fileName = "";

        if (!file.isEmpty()) {
            extension = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            fileName = randomUUID + "." + extension.trim();

            // create path object for object
            Path path = Paths.get(serverPath + fileName);

            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                log.error("UploadOne(MultipartFile file)= {}", e);
            }
        } else {
            String reason = "File is not found in body.";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, reason);
        }

        File fileModel = new File();
        fileModel.setUuid(randomUUID);
        fileModel.setExtension(extension);
        fileModel.setSize(Float.valueOf(file.getSize()));
        fileModel.setIsEnabled(true);

        fileRepository.insert(fileModel);

        FileDto fileDto = fileMapper.fromModel(fileModel);
        fileDto.setName(fileName);
        fileDto.setUri(uri + fileName);

        return fileDto;
    }

    /**
     * Update File
     * 
     * @param file
     * @param id
     * @return
     */
    private FileDto update(MultipartFile file, Long id) {
        String randomUUID = UUID.randomUUID().toString();
        String extension = "";
        // randomUUID + extension = fileName
        String fileName = "";

        if (!file.isEmpty()) {
            extension = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            fileName = randomUUID + "." + extension.trim();
            // create path object for object
            Path path = Paths.get(serverPath + fileName);
            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                log.error("UploadOne(MultipartFile file)= {}", e);
            }
        } else {
            String reason = "File is not found in body.";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, reason);
        }

        File fileModel = new File();
        fileModel.setId(id);
        fileModel.setUuid(randomUUID);
        fileModel.setExtension(extension);
        fileModel.setSize(Float.valueOf(file.getSize()));
        fileModel.setIsEnabled(true);

        fileRepository.update(fileModel);

        FileDto fileDto = fileMapper.fromModel(fileModel);
        fileDto.setName(fileName);
        fileDto.setUri(uri + fileName);

        return fileDto;
    }

    @Override
    public void deleteFileByUUID(String uuid) {

        File fileRes = fileRepository.selectByUUID(uuid).orElseThrow(() -> {
            String reason = "File resource cannot be deleted";
            Throwable cause = new Throwable("File with UUID = " + uuid + " is not found in DB");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, reason, cause);
        });
        FileDto dto = fileMapper.fromModel(fileRes);
        try {
            fileRepository.deleteByUUID(uuid);
            Path path = Paths.get(serverPath + dto.getUuid().trim() + "." + dto.getExtension().trim());
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // @Override
    // public FileDto downloadImage(String uuid) {

    //     Optional<File> opImgae = fileRepository.selectByUUID(uuid);
    //     File image = opImgae.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
    //             "File with UUID = " + uuid + " is not found in DB"));

    //     FileDto imageDto = fileMapper.fromModel(image);

    //     String name = imageDto.getUuid() + "." + imageDto.getExtension();
    //     imageDto.setName(name);
    //     imageDto.setUri(uri + name);

    //     return imageDto;
    // }

    @Override
    public FileDto countDownloadImage(String uuid) {
        File downloadCount = fileRepository.selectDownloadCount(uuid);
        FileDto downloadCountDto = fileMapper.fromModel(downloadCount);

            if (downloadCountDto.getDownload() == null) {
                fileRepository.udpateDownloadCount(0, uuid);
                String name = downloadCountDto.getUuid() + "." + downloadCountDto.getExtension();
                downloadCountDto.setName(name);
                downloadCountDto.setUri(uri + name);
            } else {
                fileRepository.udpateDownloadCount(downloadCountDto.getDownload(), uuid);
                String name = downloadCountDto.getUuid() + "." + downloadCountDto.getExtension();
                downloadCountDto.setName(name);
                downloadCountDto.setUri(uri + name);
            }
    
        return downloadCountDto;
    }

    @Override
    public FileDto showDownloadCount(String uuid) {

    File downloadCount = fileRepository.selectByUUID(uuid).orElseThrow(() -> {
            String reason = "File resource cannot be found";
            Throwable cause = new Throwable("File with UUID = " + uuid + " is not found in DB");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, reason, cause);
        });
        FileDto imageDto = fileMapper.fromModel(downloadCount);
        downloadCount = fileRepository.selectDownloadCount(uuid);
        

        String name = imageDto.getUuid() + "." + imageDto.getExtension();
        imageDto.setName(name);
        imageDto.setUri(uri + name);
        return imageDto;
    }

    
}
