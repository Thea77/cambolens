package co.istad.cambolens.api.post;

import co.istad.cambolens.api.file.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer id;
    private String name;
    private String description;
    private File icon;
    private Boolean isEnabled;
}
