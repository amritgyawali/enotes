package com.amrit.repository;

import com.amrit.dto.TodoDto;
import com.amrit.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {

    List<Todo> findByCreatedBy(Integer userId);
}
