package co.istad.cambolens.api.post.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.post.Post;
import co.istad.cambolens.api.post.mapper.PostMapper;
import co.istad.cambolens.api.post.web.PostDto;
import co.istad.cambolens.api.post.web.PostFilter;
import co.istad.cambolens.data.repository.PostRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final PostMapper postMapper;

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
            String fileName = postDto.getPhoto().getUuid() + "." + postDto.getPhoto().getExtension().trim();
            String fileUri = uri + fileName;
            postDto.getPhoto().setName(fileName);
            postDto.getPhoto().setUri(fileUri);
        }
        return postDtoListPageInfo;
    }
    
}
