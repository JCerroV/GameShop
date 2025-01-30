package com.joaquincv.juegos.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.RelationShip;

@Repository
public interface RelationShipRepository extends JpaRepository<RelationShip, Long> {
	
	@Query("SELECT r FROM RelationShip r WHERE r.user.id = :id AND r.accept=true")
	List<RelationShip> getFriends(@Param ("id")Long id);
	
	@Query("SELECT r FROM RelationShip r WHERE r.friend.id = :id AND r.accept = true")
	List<RelationShip> getFriendsReverse(@Param ("id")Long id);
	
	@Query("SELECT r FROM RelationShip r WHERE r.user.id = :id AND r.accept= false")
	List<RelationShip> getPendingFriends(@Param ("id")Long id);
	
	@Query("SELECT r FROM RelationShip r WHERE r.friend.id = :id AND r.accept= false")
	List<RelationShip> getPendingFriendsReverse(@Param ("id")Long id);
	
	@Query("SELECT r FROM RelationShip r WHERE r.friend.id = :id AND r.user.id= :userId")
	RelationShip getRelation(@Param ("id")Long id ,@Param("userId")Long userId);
	

}
