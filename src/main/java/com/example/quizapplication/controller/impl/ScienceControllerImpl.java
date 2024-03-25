package com.example.quizapplication.controller.impl;

import com.example.quizapplication.controller.abs.ScienceController;
import com.example.quizapplication.payload.AddScienceDTO;
import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.payload.ScienceDTO;
import com.example.quizapplication.service.abs.ScienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScienceControllerImpl implements ScienceController {


    @Autowired
    private ScienceService scienceService;

    @Override
    public ApiResult<?> add(AddScienceDTO addScienceDTO) {
        return scienceService.add(addScienceDTO);
    }

    @Override
    public List<ScienceDTO> getAll() {
        return scienceService.getAll();
    }

    @Override
    public ScienceDTO get(Integer id) {
        return scienceService.get(id);
    }

    @Override
    public ApiResult<?> edit(Integer id, AddScienceDTO addScienceDTO) {
        return scienceService.edit(id, addScienceDTO);
    }

    @Override
    public ApiResult<?> delete(Integer id) {
        return scienceService.delete(id);
    }
}
