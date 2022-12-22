package co.istad.cambolens.api.post.web;
import co.istad.cambolens.api.file.dto.FileDto;
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
public class CategoryDto {
    private Integer id;
    private String name;
    private String description;
    private FileDto icon;
}

