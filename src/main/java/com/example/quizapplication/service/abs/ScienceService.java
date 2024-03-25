package com.example.quizapplication.service.abs;

import com.example.quizapplication.payload.AddScienceDTO;
import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.payload.ScienceDTO;

import java.util.List;

public interface ScienceService {


    ApiResult<?> add(AddScienceDTO addScienceDTO);


    List<ScienceDTO> getAll();


        ScienceDTO get( Integer id);


    ApiResult<?> edit( Integer id, AddScienceDTO addScienceDTO);


    ApiResult<?> delete( Integer id);

}
