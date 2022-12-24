package co.istad.cambolens.api.post.web;

import co.istad.cambolens.api.post.Category;
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
public class PostFilter {
    private String title;
    private String poster;
}
