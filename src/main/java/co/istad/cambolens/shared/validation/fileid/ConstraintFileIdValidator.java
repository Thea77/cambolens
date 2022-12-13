package co.istad.cambolens.shared.validation.fileid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import co.istad.cambolens.api.file.service.FileServiceImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConstraintFileIdValidator implements ConstraintValidator<ConstraintFileId, Long> {

    private final FileServiceImpl fileService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return fileService.existsFileID(id);
    } 
    
}
