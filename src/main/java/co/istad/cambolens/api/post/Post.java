package co.istad.cambolens.api.post;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.user.User;
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
public class Post {
    private Long id;
    private User author;
    private String title;
    private String description;
    private File photo;
    private Date datePublished;
    private Integer like;
    private String location;
    private Boolean isEnabled;

    private List<Category> categories;
}
