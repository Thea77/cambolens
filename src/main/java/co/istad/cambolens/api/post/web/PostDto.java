package co.istad.cambolens.api.post.web;
import java.util.Date;
import java.util.List;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.file.dto.FileDto;
import co.istad.cambolens.api.user.User;
import co.istad.cambolens.api.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostDto {
   
    private Long id;
    private String title;
    private String description;
    private FileDto photo;
    private UserDto author;
    private Date datePublished;
    private Integer like;
    private String location;

    private List<CategoryDto> categories;
}
