package com.example.quizapplication.controller.abs;


import com.example.quizapplication.payload.AddScienceDTO;
import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.payload.ScienceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("sciences")
public interface ScienceController {


    @PostMapping
    ApiResult<?> add(@RequestBody AddScienceDTO addScienceDTO);


    @GetMapping
    List<ScienceDTO> getAll();


    @GetMapping("/{id}")
    ScienceDTO get(@PathVariable Integer id);


    @PutMapping("/{id}")
    ApiResult<?> edit(@PathVariable Integer id,
                      @RequestBody  AddScienceDTO addScienceDTO);


    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable Integer id);


}
