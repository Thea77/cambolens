package co.istad.cambolens.api.post.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/top-download")
    ResponseEntity<?> doGetTopDownloadPosts(@RequestBody(required = false) PostFilter postFilter,
                               @RequestParam(required = false, defaultValue = "1") int pageNum,
                               @RequestParam(required = false, defaultValue = "10") int pageSize) {

        PageInfo<PostDto> postDtoList = postServiceImpl.getTopDownloadPosts(postFilter, pageNum, pageSize);

        Rest<PageInfo<PostDto>> rest = new Rest<>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Top 10 Posts Downloaded have been fetched");
        rest.setData(postDtoList);

        return ResponseEntity.ok(rest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> doSelectPostById(@PathVariable("id") Long id){
        PostDto postDto = postServiceImpl.selectPostById(id);
        Rest<Object> rest = Rest.ok()
                    .setCode(HttpStatus.OK.value())
                    .setData(postDto)
                    .setMessage("Post have been fetched");
        return ResponseEntity.ok(rest);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreatePost(@Valid @RequestBody CreatePostDto body){
        PostDto postDto = postServiceImpl.createPost(body);
        // log.info("Body={}",body);
        Rest<Object> rest = Rest.ok()
                    .setCode(HttpStatus.OK.value())
                    .setData(postDto)
                    .setMessage("You have been Posted successfully.");
        return ResponseEntity.ok(rest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> doUpdatePost(@Valid @RequestBody CreatePostDto body,@PathVariable("id") Long id){
        PostDto postDto = postServiceImpl.updatePost(id,body);
        // log.info("Body={}",body);
        Rest<Object> rest = Rest.ok()
                    .setData(postDto)
                    .setMessage("You have been updated successfully.");
        return ResponseEntity.ok(rest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@Valid @PathVariable("id") Long id){
        postServiceImpl.deletePost(id);
      
        Rest<String> rest = new Rest<>();
        rest.setStatus(true);
        rest.setCode(HttpStatus.OK.value());
        rest.setMessage("Post with ID = "+id+" Have been deleted");
        rest.setData("Data Deleted");
        return ResponseEntity.ok(rest);
    }
}
