package net.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.mycomp.macrokiosk.thailand.THConfig;


public interface JPAThConfig extends JpaRepository<THConfig, Long>{

	@Query("select b from THConfig b where b.status=:status")
    List<THConfig> findEnableTHConfig(@Param("status")boolean status);

    
}