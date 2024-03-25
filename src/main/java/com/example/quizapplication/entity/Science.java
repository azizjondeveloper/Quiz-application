package com.example.quizapplication.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Science {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(nullable = false, unique = true, length = 40)
    private String title;


    @Column(nullable = false, unique = true, length = 40)
    private String url;



}
