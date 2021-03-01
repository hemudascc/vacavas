package net.jpa.repository;

import java.util.List;

import net.mycomp.messagecloud.gateway.MCGServiceConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAMCGServiceConfig extends JpaRepository<MCGServiceConfig, Long>{

	@Query("select b from MCGServiceConfig b where b.status=:status")
    List<MCGServiceConfig> findEnableMCGServiceConfig(@Param("status")boolean status);

    
}