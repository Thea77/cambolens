package co.istad.cambolens.api.post.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.file.File;
import co.istad.cambolens.api.file.dto.FileDto;
import co.istad.cambolens.api.file.service.FileServiceImpl;
import co.istad.cambolens.api.post.Category;
import co.istad.cambolens.api.post.Post;
import co.istad.cambolens.api.post.mapper.PostMapper;
import co.istad.cambolens.api.post.web.CreatePostDto;
import co.istad.cambolens.api.post.web.PostDto;
import co.istad.cambolens.api.post.web.PostFilter;
import co.istad.cambolens.api.user.User;
import co.istad.cambolens.config.security.CustomUserSecurity;
import co.istad.cambolens.data.repository.CategoryRepository;
import co.istad.cambolens.data.repository.FileRepository;
import co.istad.cambolens.data.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final FileServiceImpl fileServiceImpl;
    private final CategoryRepository categoryRepository;

    // @Value("${file.server-path}")
    // private String serverPath;

    @Value("${file.uri}")
    private String uri;

    @Override
    public PageInfo<PostDto> getAllposts(PostFilter postFilter, int pageNum, int pageSize) {
        Post post = postMapper.fromPostFilter(postFilter);

        Page<Post> postList = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> postRepository.select(post));
        PageInfo<Post> postListPageInfo = new PageInfo<>(postList);

        PageInfo<PostDto> postDtoListPageInfo = postMapper.fromModelList(postListPageInfo);

        for (PostDto postDto : postDtoListPageInfo.getList()) {
            // for (FileDto photoId : postDto.getPhoto()) {
            //     // to response Photo obj in post
            //     String fileName = photoId.getUuid() + "." + photoId.getExtension().trim();
            //     String fileUri = uri + fileName;
            //     photoId.setName(fileName);
            //     photoId.setUri(fileUri);
            // }
            String fileName = postDto.getPhoto().getUuid() + "." + postDto.getPhoto().getExtension().trim();
            String fileUri = uri + fileName;
            postDto.getPhoto().setName(fileName);
            postDto.getPhoto().setUri(fileUri);

        }
        return postDtoListPageInfo;
    }

    @Override
    public List<PostDto> getTopDownloadPosts() {
        List<Post> postsList = postRepository.selectTopDownload();
        List<PostDto> dtos = postMapper.toPostDtoForTopDownload(postsList);


        for (PostDto postDto : dtos) {
            String fileName = postDto.getPhoto().getUuid() + "." + postDto.getPhoto().getExtension().trim();
            String fileUri = uri + fileName;
            postDto.getPhoto().setName(fileName);
            postDto.getPhoto().setUri(fileUri);

        }
           
        return dtos;
    }

    @Override
    public PostDto selectPostById(Long id) {
          if (postRepository.existsById(id)) {
            Post post = postRepository.selectById(id);
            PostDto postDto = postMapper.fromModel(post);
            postDto.setPhoto(fileServiceImpl.getFileByID(post.getPhoto().getId()));
            return postDto;
        } else {
            String reason = "Post could not be founded.";
            Throwable cause = new Throwable("Post with ID = " + id + " is not found in DB.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, reason, cause);
        }
    }


    @Override
    public PostDto createPost(CreatePostDto body) {
        Post post = postMapper.fromCreatePostDto(body);
        post.setIsEnabled(body.getDatePublic());
        post.setPhoto(new File(body.getPhotoId()));

        // Get User Principle
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserSecurity customUserSecurity = (CustomUserSecurity) auth.getPrincipal();
        // Set Author
        post.setAuthor(customUserSecurity.getUser());

        try {
            if (post.getId() == null) {
                post.setDatePublished(new Date(System.currentTimeMillis()));
                postRepository.insert(post);

                // Define posts_categories
                List<Category> categories = new ArrayList<>();
                for (Integer cateId : body.getCategoriesId()) {
                    postRepository.insertPostCategory(post.getId(), cateId);
                    // to response genre obj in book
                    categories.add(categoryRepository.selectById(cateId));
                }
                post.setCategories(categories);
            } else {
                postRepository.update(post);
                postRepository.deletePostCategory(post.getId());
                // Update genre of book
                body.getCategoriesId().forEach(cateId -> postRepository.insertPostCategory(post.getId(), cateId));
            }

        } catch (Exception e) {
            log.error("Error = {}", e.getMessage());
            String reason = "You cannot save the record.";
            Throwable cause = new Throwable("Server went wrong, please contact developers or try again.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
        }

        PostDto postDto = postMapper.fromModel(post);
        // go to file by ID
        FileDto fileDto = fileServiceImpl.getFileByID(body.getPhotoId());
        postDto.setPhoto(fileDto);

        return postDto;

    }


    @Override
    public PostDto updatePost(Long id,CreatePostDto body) {
        Post post = postMapper.fromCreatePostDto(body);
        post.setId(id);
        post.setIsEnabled(body.getDatePublic());
        post.setPhoto(new File(body.getPhotoId()));

        // Get User Principle
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserSecurity customUserSecurity = (CustomUserSecurity) auth.getPrincipal();
        // Set Author
        post.setAuthor(customUserSecurity.getUser());

        if (body.getDatePublic()) {
            post.setDatePublished(new Date(System.currentTimeMillis()));
        }

        postRepository.update(post);

        // remove category from post
        postRepository.deletePostCategory(id);
        // and then redefine categories
        List<Category> categories = new ArrayList<>();
        for (Integer cateId : body.getCategoriesId()) {
            postRepository.insertPostCategory(post.getId(), cateId);
            // to response category obj in post
            categories.add(categoryRepository.selectById(cateId));
        }
        post.setCategories(categories);

        return this.selectPostById(post.getId());
      
    }


    @Override
    public void deletePost(Long id) {

        if (postRepository.existsById(id)) {
            postRepository.deletePost(id);
        } else {
            String reason = "Delete post operation is failed.";
            Throwable cause = new Throwable("Post with ID = " + id + " is not found in DB.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, reason, cause);
        }
    }

}
