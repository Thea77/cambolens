package co.istad.cambolens.api.file.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.file.dto.FileDto;
import co.istad.cambolens.api.file.service.FileServiceImpl;
import co.istad.cambolens.shared.rest.Rest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/files")
public class FileRestConroller {
    private final FileServiceImpl fileServiceImpl;

    
    @GetMapping
    public ResponseEntity<?> getFiles(
        @RequestParam(required = false, defaultValue = "1") Integer pageNum ,
        @RequestParam(required = false, defaultValue = "20") Integer pageSize ) {
            
        PageInfo<FileDto> fileDtos = fileServiceImpl.findAllFiles(pageNum,pageSize);
        var rest = new Rest<PageInfo<FileDto>>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Files Have been Fetched");
        rest.setData(fileDtos);    
        // log.info("Books= {}",fileDtos);
        return ResponseEntity.ok(rest); 
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilesById(@PathVariable("id") Long id){
        var imageDto = fileServiceImpl.getFileByID(id);

        var rest = new Rest<FileDto>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Files Have been fetched");
        rest.setData(imageDto);    
        // log.info("File= {}",imageDtoList);
        return ResponseEntity.ok(rest); 
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> doUploadOne(@RequestPart("file") MultipartFile file){
        var imageDto = fileServiceImpl.uploadOne(file);

        var rest = new Rest<FileDto>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("File Have been uploaded");
        rest.setData(imageDto);    
        // log.info("File= {}",imageDto);
        return ResponseEntity.ok(rest); 
    }

    @PostMapping(path = "/upload-all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> doUploadAll(@RequestPart("files") List<MultipartFile> files){
        var imageDtoList = fileServiceImpl.uploadAll(files);

        var rest = new Rest<List<FileDto>>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Files Have been uploaded");
        rest.setData(imageDtoList);    
        // log.info("File= {}",imageDtoList);
        return ResponseEntity.ok(rest); 
    }

    @PutMapping(path = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> doUpdateImage(@RequestPart("file") MultipartFile file, @PathVariable("id") Long id){
        var imageDto = fileServiceImpl.updateFile(file, id);

        var rest = new Rest<FileDto>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("File Have been updated");
        rest.setData(imageDto);    
        // log.info("File= {}",imageDto);
        return ResponseEntity.ok(rest); 
    }

    @DeleteMapping("/{uuid}")
    ResponseEntity<?> deleteFileByUUID(@PathVariable String uuid) {

        fileServiceImpl.deleteFileByUUID(uuid);

        var rest = new Rest<String>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("File has been deleted.");
        rest.setData(uuid);

        return ResponseEntity.ok(rest);
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<?> downloadFile(@PathVariable String uuid) {
       FileDto fileDto = fileServiceImpl.countDownloadImage(uuid);
        var rest = new Rest<FileDto>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Files Have been downloaded");
        rest.setData(fileDto);    
        // log.info("File= {}",imageDtoList);
        return ResponseEntity.ok(rest); 
    }

    @GetMapping("/count-download/{uuid}")
    public ResponseEntity<?> doShowDownloadCount(@PathVariable String uuid) {
    FileDto fileDto = fileServiceImpl.showDownloadCount(uuid);
        var rest = new Rest<FileDto>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("This image have been downloaded for "+ fileDto.getDownload() +" times");
        rest.setData(fileDto);    
        // log.info("File= {}",imageDtoList);
        return ResponseEntity.ok(rest); 
    }

}
