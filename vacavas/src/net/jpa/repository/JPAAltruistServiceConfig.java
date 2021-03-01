package net.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.mycomp.altruist.AltruistServiceConfig;

public interface JPAAltruistServiceConfig extends JpaRepository<AltruistServiceConfig, Long>{
	@Query("select b from AltruistServiceConfig b where b.status=:status")
    List<AltruistServiceConfig> findEnableAltruistServiceConfig(@Param("status")boolean status);
}
