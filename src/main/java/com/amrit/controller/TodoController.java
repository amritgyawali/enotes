package com.amrit.controller;

import com.amrit.dto.TodoDto;
import com.amrit.exception.ResourceNotFoundException;
import com.amrit.service.TodoService;
import com.amrit.util.CommonUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception {
        Boolean saveTodo = todoService.saveTodo(todoDto);
        if (saveTodo){
            return CommonUtil.createBuildResponseMessage("Todo saved successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Todo not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception {
        TodoDto todoById = todoService.getTodoById(id);
        return CommonUtil.createBuildResponse(todoById,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTodoByUser(){
        List<TodoDto> todoByUser = todoService.getTodoByUser();
        if (ObjectUtils.isEmpty(todoByUser)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(todoByUser,HttpStatus.OK);
    }

}
