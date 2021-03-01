package net.jpa.repository;

import java.util.List;

import net.mycomp.messagecloud.gateway.MccMncNetwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JPAMccMncNetwork extends JpaRepository<MccMncNetwork, Long>{

	@Query("select b from MccMncNetwork b where b.status=:status")
    List<MccMncNetwork> findEnableMccMncNetwork(@Param("status")boolean status);

}