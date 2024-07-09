package com.joaquincv.juegos.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.ChatMessage;
import com.joaquincv.juegos.app.models.entities.RelationShip;
import com.joaquincv.juegos.app.models.entities.User;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

	@Query("SELECT cm FROM ChatMessage cm WHERE cm.relationShip.id = :relationShipId")
    List<ChatMessage> findByRelationShip(@Param(value = "relationShipId") Long relationShipId);
	
	@Query("SELECT m FROM ChatMessage m WHERE m.receiver.id = :username AND m.read = false")
    List<ChatMessage> findUnreadMessagesByUsername(@Param("username") Long id);
	
	
}
