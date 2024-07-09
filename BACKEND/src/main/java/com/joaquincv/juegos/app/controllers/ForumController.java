package com.joaquincv.juegos.app.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.joaquincv.juegos.app.bean.input.ThreadRequest;
import com.joaquincv.juegos.app.bean.output.PostDTO;
import com.joaquincv.juegos.app.bean.output.ThreadDTO;
import com.joaquincv.juegos.app.services.ForumService;

@RestController
@CrossOrigin("*")
public class ForumController {
	
	@Autowired
	ForumService forumService;
	
	@GetMapping("/forum")
	public ResponseEntity<Page<ThreadDTO>> getThreads(
			@RequestParam(defaultValue = "0") int page ,
			@RequestParam(defaultValue = "id") String order,
			@RequestParam(required = false) String filter){
		try {
			return new ResponseEntity<Page<ThreadDTO>>(forumService.getThreads(PageRequest.of(page, 10,Sort.by(order)) ,filter),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Page<ThreadDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/forum/new")
	public ResponseEntity<String>createThread(@RequestBody ThreadRequest request,Principal principal){
		try {
			return new ResponseEntity<String>(forumService.createNewThread(request.getTitle(),request.getContent(),principal.getName()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/forum/{id}")
	public ResponseEntity<ThreadDTO>getThread(@PathVariable Long id){
		try {
			return new ResponseEntity<ThreadDTO>(forumService.getThread(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ThreadDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("forum/{id}/update")
	public ResponseEntity<ThreadDTO>updateThread(@PathVariable Long id, @RequestBody ThreadDTO threadDTO, Principal principal){
		try {
			return new ResponseEntity<ThreadDTO>(forumService.updateThread(id,threadDTO,principal.getName()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ThreadDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("forum/{id}/delete")
	public ResponseEntity<String> deleteThread(@PathVariable Long id, Principal principal){
		try {
			forumService.deleteThread(id,principal.getName());
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@GetMapping("/forum/{id}/posts")
	public ResponseEntity<Page<PostDTO>> getPosts(@PathVariable Long id, @RequestParam(defaultValue = "0") int page){
		try {
			return new ResponseEntity<Page<PostDTO>>(forumService.getPosts(id ,PageRequest.of(page, 10)),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Page<PostDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("forum/{id}/newPost")
	public ResponseEntity<String>createNewPost(@PathVariable Long id,  @RequestBody String content,Principal principal){
		try {
			forumService.createNewPost(id,content,principal.getName());
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("forum/post/{postId}/update")
	public ResponseEntity<String> updatePost(@PathVariable Long postId ,@RequestBody String content, Principal principal){
		try {
			forumService.updatePost(postId,content,principal.getName());
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/forum/post/{postId}/delete")
	public ResponseEntity<String>deletePost(@PathVariable Long postId ,Principal principal){
		try {
			forumService.deletePost(postId,principal.getName());
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
