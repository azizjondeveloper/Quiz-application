package com.example.quizapplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(nullable = false, unique = true, length = 200)
    private String title;


    @Column(nullable = false, unique = true, length = 100)
    private String option1;


    @Column(nullable = false, unique = true, length = 100)
    private String option2;


    @Column(nullable = false, unique = true, length = 100)
    private String option3;


    @Column(nullable = false, unique = true, length = 100)
    private String option4;


    @Column(nullable = false, unique = true, length = 100)
    private String correctOption;


    @ManyToOne
    private Science science;


}
