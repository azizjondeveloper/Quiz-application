package com.example.quizapplication.repository;

import com.example.quizapplication.entity.Science;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
    public interface ScienceRepository extends JpaRepository<Science, Integer> {
}
