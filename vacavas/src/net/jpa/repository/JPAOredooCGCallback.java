package net.jpa.repository;

import java.util.List;
import net.mycomp.oredoo.kuwait.OredooKuwaitCGCallback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAOredooCGCallback extends JpaRepository<OredooKuwaitCGCallback, Long>{

	@Query("select b from OredooKuwaitCGCallback b where  b.msisdn=:msisdn and b.used=:used order by b.id desc")
	List<OredooKuwaitCGCallback> findOredooKuwaitCGCallbackByMsisdn(@Param("msisdn")String  msisdn,
			@Param("used")boolean  used);

    
}