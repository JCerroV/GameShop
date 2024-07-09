package com.joaquincv.juegos.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.Thread;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
	
	Page<Thread> findByTitleContaining(String title, Pageable pageable);

}
