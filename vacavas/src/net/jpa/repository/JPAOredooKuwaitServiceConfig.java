package net.jpa.repository;

import java.util.List;

import net.mycomp.oredoo.kuwait.OredooKuwaitServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAOredooKuwaitServiceConfig extends JpaRepository<OredooKuwaitServiceConfig, Long>{

	@Query("select b from OredooKuwaitServiceConfig b where b.status=:status")
    List<OredooKuwaitServiceConfig> findEnableOredoConfig(@Param("status")boolean status);

    
}