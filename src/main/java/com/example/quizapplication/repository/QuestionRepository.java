package com.example.quizapplication.repository;

import com.example.quizapplication.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
