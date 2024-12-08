package com.SpringBootProject.proj1.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootProject.proj1.Entitys.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}
