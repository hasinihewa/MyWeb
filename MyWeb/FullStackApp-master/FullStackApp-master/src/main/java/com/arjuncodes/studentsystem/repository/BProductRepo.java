package com.arjuncodes.studentsystem.repository;
import com.arjuncodes.studentsystem.model.BeautyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BProductRepo extends JpaRepository<BeautyProduct, Integer> {
}


