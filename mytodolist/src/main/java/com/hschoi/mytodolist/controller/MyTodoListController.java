package com.hschoi.mytodolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hschoi.mytodolist.dto.TodoListDTO;
import com.hschoi.mytodolist.service.MyTodoListService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hychoi
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/myTodoList")
public class MyTodoListController {
	@Autowired
    private MyTodoListService todoListService;
	
	@PostMapping(
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public TodoListDTO.CreateResponse createTodo (@RequestBody TodoListDTO.CreateRequest todoDTO) {
		log.info("ID를 이용하여 등록된 할일을 등록합니다.");
        return todoListService.create(todoDTO);
    }
	
	@GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public TodoListDTO.FindOneResponse getTodo (@PathVariable("id") int id) {
		log.info("ID를 이용하여 할일을 조회합니다.");
        return todoListService.get(id);
    }
	
	@PutMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public TodoListDTO.UpdateResponse updateTodo (@PathVariable("id") int id, @RequestBody TodoListDTO.UpdateRequest todoDTO) {
		log.info("ID를 이용하여 할일을 수정합니다.");
		return todoListService.update(id, todoDTO);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public TodoListDTO.DeleteResponse deleteTodo (@PathVariable("id") int id) {
    	log.info("ID를 이용하여 할일을 삭제합니다.");
        return todoListService.delete(id);
    }

    @PostMapping(
            value = "/{id}/complete",
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public TodoListDTO.UpdateResponse complete (@PathVariable("id") int id) {
    	log.info("ID를 이용하여 할일을 완료처리합니다.");
    	return todoListService.complete(id);
    }
    
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }
    )
    public Page<TodoListDTO.FindAllResponse> getAllTodoList (Pageable pageable) {
    	log.info("ID를 이용하여 등록된 할일을 조회합니다.");
    	return todoListService.getAllTodoList(pageable);
    }
}
