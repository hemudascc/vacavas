package net.jpa.repository;

import net.persist.bean.AdnetworkToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAAdnetworkToken extends JpaRepository<AdnetworkToken, Long>{

	@Query("select b from AdnetworkToken b where b.tokenId=:tokenId")
	AdnetworkToken findEnableAdnetworkToken(@Param("tokenId")Integer tokenId);
}