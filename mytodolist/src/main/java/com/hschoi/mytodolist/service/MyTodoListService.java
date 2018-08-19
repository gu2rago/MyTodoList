package com.hschoi.mytodolist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hschoi.mytodolist.dto.TodoListDTO;

public interface MyTodoListService {
	// 할일을 생성한다.
	TodoListDTO.CreateResponse create(TodoListDTO.CreateRequest todoListDTO);
	// 할일 단위로 조회한다.
	TodoListDTO.FindOneResponse get(int id);
	// 할일 단위로 수정한다.
	TodoListDTO.UpdateResponse update(int id, TodoListDTO.UpdateRequest todoListDTO);
	// 할일 단위로 삭제한다.
	TodoListDTO.DeleteResponse delete(int id);
	// 할일 단위 완료 한다.
	TodoListDTO.UpdateResponse complete(int id);
	// 모든 할일을 리스트로 조회한다.
	Page<TodoListDTO.FindAllResponse> getAllTodoList(Pageable pageable);
}
