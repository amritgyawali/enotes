package com.amrit.service.impl;

import com.amrit.dto.TodoDto;
import com.amrit.entity.Todo;
import com.amrit.enums.TodoStatus;
import com.amrit.exception.ResourceNotFoundException;
import com.amrit.repository.TodoRepository;
import com.amrit.service.TodoService;
import com.amrit.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception {
        validation.todoValidation(todoDto);
    Todo todo = mapper.map(todoDto, Todo.class);
    todo.setStatusId(todoDto.getStatus().getId());
    Todo saveTodo=todoRepo.save(todo);
    if(ObjectUtils.isEmpty(saveTodo)){
        return false;
    }
    return true;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception {
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found"));
        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    public void setStatus(TodoDto todoDto,Todo todo){
        for (TodoStatus st:TodoStatus.values()){
            if (st.getId().equals(todo.getStatusId())){
                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                        .id(st.getId())
                        .name(st.getName())
                        .build();
                todoDto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId =2;
        List<Todo> todo = todoRepo.findByCreatedBy(userId);
        return todo.stream().map(td->mapper.map(td, TodoDto.class)).toList();
    }
}
