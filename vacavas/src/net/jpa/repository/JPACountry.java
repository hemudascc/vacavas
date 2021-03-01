package net.jpa.repository;


import net.persist.bean.Country;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JPACountry extends JpaRepository<Country, Long>{

}