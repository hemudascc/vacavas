package net.jpa.repository;

import java.util.List;

import net.mycomp.mobimind.MobimindServiceConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAMobimindServiceConfig extends JpaRepository<MobimindServiceConfig, Long>{

	@Query("select b from MobimindServiceConfig b where b.status=:status")
    List<MobimindServiceConfig> findEnableMobimindServiceConfig(@Param("status")boolean status);

    
}