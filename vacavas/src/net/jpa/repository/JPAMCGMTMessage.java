package net.jpa.repository;

import net.mycomp.messagecloud.gateway.MCGMTMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAMCGMTMessage extends JpaRepository<MCGMTMessage, Long>{

	@Query("select b from MCGMTMessage b where b.id=:id")
	MCGMTMessage findMCGMTMessageById(@Param("id")Integer id);
 
	@Query("select b from MCGMTMessage b where b.messageId=:messageId")
	MCGMTMessage findMCGMTMessageByMessageId(@Param("messageId")String messageId);
	
    
}