package com.example.quizapplication.service.abs;

import com.example.quizapplication.payload.AddQuestionDTO;
import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.payload.QuestionDTO;

import java.util.List;

public interface QuestionService {


    ApiResult<?> add(AddQuestionDTO addQuestionDTO);


    List<QuestionDTO> getAll();


    QuestionDTO get(Integer id);


    ApiResult<?> edit(Integer id, AddQuestionDTO addQuestionDTO);


    ApiResult<?> delete(Integer id);

}
