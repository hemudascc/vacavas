package net.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.mycomp.macrokiosk.thailand.THMTMessage;


public interface JPATHMTMessage extends JpaRepository<THMTMessage, Long>{

	@Query("select b from THMTMessage b where b.id=:id")
	THMTMessage findMTMessage(@Param("id")Integer id);
	
	@Query("select b from THMTMessage b where b.msgId=:msgId order by b.id desc")
	List<THMTMessage> findMTMessageByMessageId(@Param("msgId")String msgId);

    
}