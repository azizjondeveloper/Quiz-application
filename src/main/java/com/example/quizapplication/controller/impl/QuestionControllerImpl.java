package com.example.quizapplication.controller.impl;

import com.example.quizapplication.controller.abs.QuestionController;
import com.example.quizapplication.payload.AddQuestionDTO;
import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.payload.QuestionDTO;
import com.example.quizapplication.service.abs.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionControllerImpl implements QuestionController {


    @Autowired
    private QuestionService questionService;

    @Override
    public ApiResult<?> add(AddQuestionDTO addQuestionDTO) {
        return questionService.add(addQuestionDTO);
    }

    @Override
    public List<QuestionDTO> getAll() {
        return questionService.getAll();
    }

    @Override
    public QuestionDTO get(Integer id) {
        return questionService.get(id);
    }

    @Override
    public ApiResult<?> edit(Integer id, AddQuestionDTO addQuestionDTO) {
        return questionService.edit(id, addQuestionDTO);
    }

    @Override
    public ApiResult<?> delete(Integer id) {
        return questionService.delete(id);
    }
}
