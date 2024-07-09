package com.joaquincv.juegos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaquincv.juegos.app.models.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
