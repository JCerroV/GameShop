package com.joaquincv.juegos.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.ThreadPost;

@Repository
public interface ThreadPostRepository extends JpaRepository<ThreadPost, Long> {
	
	Page<ThreadPost> findByThreadId(Long threadId, Pageable pageable);

}
