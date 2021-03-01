package net.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.mycomp.oredoo.kuwait.OredooKuwaitCGNotification;

public interface JPAOredooCGNotification extends JpaRepository<OredooKuwaitCGNotification, Long>{

	@Query("select b from OredooKuwaitCGNotification b where b.id=:id")
    OredooKuwaitCGNotification findOredooKuwaitCGNotificationById(@Param("id")Integer  id);

    
}
