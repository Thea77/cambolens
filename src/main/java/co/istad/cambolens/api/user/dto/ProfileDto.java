package co.istad.cambolens.api.user.dto;

import co.istad.cambolens.shared.constraint.fileid.ConstraintFileId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    
    @ConstraintFileId
    private Long profileId;
}
