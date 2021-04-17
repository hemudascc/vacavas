package net.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import net.mycomp.timwe.TimweServiceConfig;

public interface JPATimweServiceConfig extends JpaRepository<TimweServiceConfig,Long>{
	
	@Query("select b from TimweServiceConfig b where b.status=:status")
    List<TimweServiceConfig> findEnableTimweServiceConfig(@Param("status")boolean status);
	
}
