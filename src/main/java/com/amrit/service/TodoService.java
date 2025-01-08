package com.amrit.service;

import com.amrit.dto.TodoDto;
import com.amrit.entity.Todo;
import com.amrit.exception.ResourceNotFoundException;

import java.util.List;

public interface TodoService {

    public Boolean saveTodo(TodoDto todoDto) throws Exception;

    TodoDto getTodoById(Integer id) throws ResourceNotFoundException, Exception;

    List<TodoDto> getTodoByUser();
}
