package co.istad.cambolens.api.file.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.file.dto.FileDto;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileDto fromModel(File file);

    File toModel(FileDto fileDto);

    List<File> toModelList(List<FileDto> fileDtoList);

    PageInfo<FileDto> fromModelList(PageInfo<File> files);

    List<FileDto> toFileDtoList(List<File> files);

}
