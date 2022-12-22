package co.istad.cambolens.api.post.service;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.post.web.PostDto;
import co.istad.cambolens.api.post.web.PostFilter;

public interface PostService {
    
     /**
     * Get all books from database with pagination configuration
     * @param pageNum is used to configure the pagination
     * @param pageSize is used to configure the pagination
     * @return List<BookDto>
     */
    PageInfo<PostDto> getAllposts(PostFilter postFilter, int pageNum, int pageSize);
}
