package com.joaquincv.juegos.app.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.bean.output.PostDTO;
import com.joaquincv.juegos.app.bean.output.ThreadDTO;
import com.joaquincv.juegos.app.models.entities.Role;
import com.joaquincv.juegos.app.models.entities.Thread;
import com.joaquincv.juegos.app.models.entities.ThreadPost;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.ThreadPostRepository;
import com.joaquincv.juegos.app.repository.ThreadRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

@Service
public class ForumServiceImpl implements ForumService{
	
	@Autowired
	ThreadRepository threadRepository;
	
	@Autowired
	ThreadPostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public String createNewThread(String title,String content, String username) {
		try {
			if (!title.isBlank()&& !content.isBlank()&& !username.isBlank()) {
				User user=userRepository.findByUsername(username).get();
				Thread thread=new Thread(title,user);
				threadRepository.save(thread);
				postRepository.save(new ThreadPost(content,thread,user));
				return thread.getId().toString();
			}else {
				throw new Exception("Error inesperado");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public Page<ThreadDTO> getThreads(Pageable pageable , String filter) {
		try {
			
			Page<Thread>threads1;
			
			if (filter==null || filter.isBlank()) {
				threads1=threadRepository.findAll(pageable);
				
			}else {
				threads1=threadRepository.findByTitleContaining(filter,pageable);
			}
			
			Page<ThreadDTO>threads=threads1.map(thread -> new ThreadDTO(thread));
			
			
			return threads;
			
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ThreadDTO updateThread(Long id, ThreadDTO threadDTO, String username) {
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			Thread thread=threadRepository.findById(id).get();
			if (user.getRole()!=Role.USER) {
				thread.setTitle(threadDTO.getTitle());
				thread.setStatus(threadDTO.isStatus());
				thread.setUpdatedAt(new Date());
				threadRepository.save(thread);
			}
			return new ThreadDTO(thread);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ThreadDTO getThread(Long id) {
		try {
			Thread thread=threadRepository.findById(id).orElse(null);
			if (thread!=null) {
				thread.setVisit(thread.getVisit()+1);
				threadRepository.save(thread);
			}
			
		return new ThreadDTO(thread);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteThread(Long id,String username) {
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			if (user.getRole()!=Role.USER) {
				threadRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw e;
		}
		
		
	}

	@Override
	public void createNewPost(Long id,String content, String username) {
		User user=userRepository.findByUsername(username).orElse(null);
		Thread thread=threadRepository.findById(id).get();
		if (user !=null && content!=null &&!content.isBlank()) {
			ThreadPost post=new ThreadPost(content,thread,user);
			postRepository.save(post);
			thread.setPosts(thread.getPosts()+1);
			threadRepository.save(thread);
		}
	}

	@Override
	public Page<PostDTO> getPosts(Long id,Pageable pageable) {
		try {
			
			Page<ThreadPost>posts=postRepository.findByThreadId(id, pageable);
			Page<PostDTO>postDTO=posts.map(post->new PostDTO(post));
			return postDTO;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updatePost(Long id, String content, String username) {
		try {
			if (content!=null && !content.isBlank()) {
				User user=userRepository.findByUsername(username).orElse(null);
				ThreadPost post=postRepository.findById(id).orElse(null);
				if (user!=null&&post!=null) {
					if (user.getRole()!=Role.USER || post.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
						post.setContent(content);
						post.setUpdatedAt(new Date());
						post.setModifyBy(user);
						postRepository.save(post);
					}
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public void deletePost(Long id,String username) {
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			ThreadPost post=postRepository.findById(id).orElse(null);
			if (user!=null&&post!=null) {
				if (user.getRole()!=Role.USER || post.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
					postRepository.delete(post);
					post.getThread().setPosts(post.getThread().getPosts()-1);
					threadRepository.save(post.getThread());
				}
				
			}
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	

}
