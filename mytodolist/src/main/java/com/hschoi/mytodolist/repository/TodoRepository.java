package com.hschoi.mytodolist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hschoi.mytodolist.entity.TodoListEntity;

/**
* Desc :
* @Company : ebayJapan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 16
* @Version :
*/
public interface TodoRepository extends JpaRepository<TodoListEntity, Integer>, MyTodoRepository {
	Optional<TodoListEntity> findById (int id);
	void deleteById (List<Integer> ids);
}
