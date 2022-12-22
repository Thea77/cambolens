package co.istad.cambolens.api.post.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import co.istad.cambolens.api.post.service.PostServiceImpl;
import co.istad.cambolens.shared.rest.Rest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {
    
    private final PostServiceImpl postServiceImpl;

    @GetMapping
    ResponseEntity<?> getPosts(@RequestBody(required = false) PostFilter postFilter,
                               @RequestParam(required = false, defaultValue = "1") int pageNum,
                               @RequestParam(required = false, defaultValue = "20") int pageSize) {

        PageInfo<PostDto> postDtoList = postServiceImpl.getAllposts(postFilter, pageNum, pageSize);

        Rest<PageInfo<PostDto>> rest = new Rest<>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Posts have been fetched");
        rest.setData(postDtoList);

        return ResponseEntity.ok(rest);
    }
}
