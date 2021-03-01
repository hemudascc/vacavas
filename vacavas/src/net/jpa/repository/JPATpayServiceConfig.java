package net.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.mycomp.tpay.TpayServiceConfig;

public interface JPATpayServiceConfig extends JpaRepository<TpayServiceConfig, Long>{
	
	@Query("select b from TpayServiceConfig b where b.status=:status")
	List<TpayServiceConfig> findEnableTpayServiceConfig(@Param("status")boolean status);

}
