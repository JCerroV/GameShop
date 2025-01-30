package com.joaquincv.juegos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaquincv.juegos.app.models.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
