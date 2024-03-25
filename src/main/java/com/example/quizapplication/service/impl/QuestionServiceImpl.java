package com.example.quizapplication.service.impl;

import com.example.quizapplication.entity.Question;
import com.example.quizapplication.entity.Science;
import com.example.quizapplication.payload.AddQuestionDTO;
import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.payload.QuestionDTO;
import com.example.quizapplication.repository.QuestionRepository;
import com.example.quizapplication.repository.ScienceRepository;
import com.example.quizapplication.service.abs.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {


    private final QuestionRepository questionRepository;


    private final ScienceRepository scienceRepository;


    @Override
    public ApiResult<?> add(AddQuestionDTO addQuestionDTO) {
        Question question = mapAddQuestionDTOToQuestion(addQuestionDTO, new Question());
        questionRepository.save(question);
        return ApiResult.successResponse();
    }



    @Override
    public List<QuestionDTO> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(this::mapQuestionToQuestionDTO)
                .collect(Collectors.toList());
    }



    @Override
    public QuestionDTO get(Integer id) {
        return null;
    }

    @Override
    public ApiResult<?> edit(Integer id, AddQuestionDTO addQuestionDTO) {
        Question question = questionRepository.findById(id).orElseThrow(RuntimeException::new);
        Question editQuestion = mapAddQuestionDTOToQuestion(addQuestionDTO, question);
        questionRepository.save(editQuestion);
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<?> delete(Integer id) {
        questionRepository.deleteById(id);
        return ApiResult.successResponse();
    }



    private QuestionDTO mapQuestionToQuestionDTO(Question question) {
        return new QuestionDTO(question.getId(),
                question.getTitle(),
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4(),
                question.getCorrectOption(),
                question.getScience());
    }


    private Question mapAddQuestionDTOToQuestion(AddQuestionDTO addQuestionDTO, Question question) {
        Science science = scienceRepository.findById(addQuestionDTO.getScienceId()).
                orElseThrow(RuntimeException::new);
        question.setTitle(addQuestionDTO.getTitle());
        question.setOption1(addQuestionDTO.getOption1());
        question.setOption2(addQuestionDTO.getOption2());
        question.setOption3(addQuestionDTO.getOption3());
        question.setOption4(addQuestionDTO.getOption4());
        question.setCorrectOption(addQuestionDTO.getCorrectOption());
        question.setScience(science);
        return question;
    }

}
