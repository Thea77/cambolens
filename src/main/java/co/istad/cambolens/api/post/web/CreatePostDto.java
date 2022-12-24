package co.istad.cambolens.api.post.web;

import java.util.List;

import co.istad.cambolens.shared.constraint.fileid.ConstraintFileId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreatePostDto {
    // private Long authorId;
    private String title;
    private String description;
    @ConstraintFileId
    private Long photoId;
    private Boolean datePublic;
    private String location;
    private List<Integer> categoriesId;
}
