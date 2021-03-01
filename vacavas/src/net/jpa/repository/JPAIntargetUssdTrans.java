package net.jpa.repository;

import net.mycomp.intarget.InTargetUssdTrans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JPAIntargetUssdTrans extends JpaRepository<InTargetUssdTrans, Long>{

	@Query("select b from InTargetUssdTrans b where b.id=:id")
	InTargetUssdTrans findInTargetUssdTrans(@Param("id")Integer id);

    
}