package net.jpa.repository;

import java.util.List;

import net.mycomp.intarget.InTargetConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAIntargetServiceConfig extends JpaRepository<InTargetConfig, Long>{

	@Query("select b from InTargetConfig b where b.status=:status")
    List<InTargetConfig> findEnableInTargetConfig(@Param("status")boolean status);

    
}