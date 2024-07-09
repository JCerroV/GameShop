package com.joaquincv.juegos.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joaquincv.juegos.app.bean.output.PostDTO;
import com.joaquincv.juegos.app.bean.output.ThreadDTO;

public interface ForumService {
	
	
	String createNewThread(String title, String content, String username);
	
	Page<ThreadDTO>getThreads(Pageable pageable, String filter);
	
	ThreadDTO updateThread(Long id,ThreadDTO threadDTO,String username);
	
	ThreadDTO getThread(Long id);
	
	void deleteThread(Long id,String username);
	
	
	
	void createNewPost(Long id,String content,String username);
	
	Page<PostDTO>getPosts(Long id,Pageable pageable);
	
	void updatePost(Long id,String content, String username);
	
	void deletePost(Long id ,String username);
	
	
	
	

}
