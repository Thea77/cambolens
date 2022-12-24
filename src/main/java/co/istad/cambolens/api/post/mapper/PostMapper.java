package co.istad.cambolens.api.post.mapper;

import org.mapstruct.Mapper;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.post.Post;
import co.istad.cambolens.api.post.web.CreatePostDto;
import co.istad.cambolens.api.post.web.PostDto;
import co.istad.cambolens.api.post.web.PostFilter;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PageInfo<PostDto> fromModelList(PageInfo<Post> posts);

    PostDto fromModel(Post post);

    Post toModel(PostDto postDto);

    Post fromCreatePostDto(CreatePostDto createPostDto);

    CreatePostDto toCreatePostDto(Post post);

    Post fromPostFilter(PostFilter postFilter);
}
