package net.jpa.repository;

import net.persist.bean.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAProduct extends JpaRepository<Product, Long>{

}