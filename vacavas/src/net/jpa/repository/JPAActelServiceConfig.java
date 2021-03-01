package net.jpa.repository;

import java.util.List;

import net.mycomp.actel.ActelServiceConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAActelServiceConfig extends JpaRepository<ActelServiceConfig, Long>{

	@Query("select b from ActelServiceConfig b where b.status=:status")
    List<ActelServiceConfig> findEnableActelServiceConfig(@Param("status")boolean status);
}