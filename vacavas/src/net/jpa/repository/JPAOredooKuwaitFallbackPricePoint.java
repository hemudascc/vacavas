package net.jpa.repository;

import java.util.List;

import net.mycomp.oredoo.kuwait.OredoKuwaitFallbackPricePoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAOredooKuwaitFallbackPricePoint extends JpaRepository<OredoKuwaitFallbackPricePoint, Long>{

	@Query("select b from OredoKuwaitFallbackPricePoint b where b.status=:status")
    List<OredoKuwaitFallbackPricePoint> findEnableOredoKuwaitFallbackPricePoint(@Param("status")boolean status);

    
}