package com.hschoi.mytodolist.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hschoi.mytodolist.dto.TodoListDTO;
import com.hschoi.mytodolist.dto.TodoListDTO.CreateRequest;
import com.hschoi.mytodolist.dto.TodoListDTO.CreateResponse;
import com.hschoi.mytodolist.dto.TodoListDTO.DeleteResponse;
import com.hschoi.mytodolist.dto.TodoListDTO.FindAllResponse;
import com.hschoi.mytodolist.dto.TodoListDTO.UpdateRequest;
import com.hschoi.mytodolist.dto.TodoListDTO.UpdateResponse;
import com.hschoi.mytodolist.entity.DirectoryEntity;
import com.hschoi.mytodolist.entity.TodoListEntity;
import com.hschoi.mytodolist.exception.ContentNotFoundException;
import com.hschoi.mytodolist.exception.DependencyException;
import com.hschoi.mytodolist.exception.InvalidReqeustException;
import com.hschoi.mytodolist.repository.DirectoryRepository;
import com.hschoi.mytodolist.repository.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyTodoListServiceImpl implements MyTodoListService {
	
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private DirectoryRepository directoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public CreateResponse create(CreateRequest todoListDTO) {
		// TODO Auto-generated method stub

		TodoListEntity todoList = new TodoListEntity();
		Date currentDate = Calendar.getInstance().getTime();

		if (todoListDTO.getContent() == null || todoListDTO.getContent().isEmpty()) {
			throw new InvalidReqeustException("There is no contents.Check your request");
		}

		todoList.setContent(todoListDTO.getContent());
		todoList.setRegDt(currentDate);
		todoList.setModDt(currentDate);

		todoRepository.save(todoList);

		int parentId = todoList.getId();
		if (todoListDTO.getParentId() != null) {
			
			Optional<TodoListEntity> parentTodoList = getTodoEntity(todoListDTO.getParentId());
			if (parentTodoList.isPresent() == false) {
				throw new ContentNotFoundException("Can't find parent TODO(" + todoListDTO.getParentId() + ")");
			}

			if (parentTodoList.get().isCompleted()) {
				throw new RuntimeException("Parent(" + todoListDTO.getParentId() + ") is already done.");
			}

			parentId = todoListDTO.getParentId();
		}
		
		directoryRepository.createDirectoryById(parentId, todoList.getId());
		

		String upperTodo = todoRepository.getUpperTodo(todoList.getId());
		if (upperTodo == null) {
			upperTodo = todoList.getContent();
		}
		todoList.setUpperTodo(upperTodo);
		
		todoRepository.save(todoList);

		return modelMapper.map(todoList, TodoListDTO.CreateResponse.class);
	}
	/**
	 * @Desc : Todo의 Unique한 ID 값으로 Todo 조회
	 * @param id
	 * @return TodoListEntity
	 */
	private Optional<TodoListEntity> getTodoEntity(int id) {
		return todoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public TodoListDTO.FindOneResponse get(int id) {

		Optional<TodoListEntity> optionalTodo = null;
		if ( (optionalTodo = getTodoEntity(id) ).isPresent() == false) {
			throw new ContentNotFoundException("Couldn't find TODO(" + id + ")");
		}
		return modelMapper.map(optionalTodo.get(), TodoListDTO.FindOneResponse.class);
	}
	
	@Transactional(readOnly = true)
	public Page<FindAllResponse> getAllTodoList(Pageable pageable) {
		// TODO Auto-generated method stub
		return todoRepository.findAll(pageable)
				.map(todoList -> modelMapper.map(todoList, TodoListDTO.FindAllResponse.class));
	}

	@Transactional
	public UpdateResponse update(int id, UpdateRequest todoListDTO) {
//		// TODO Auto-generated method stub
		Date currentDate = Calendar.getInstance().getTime();
		Optional<TodoListEntity> optionalTodo = null;
		if ((optionalTodo = getTodoEntity(id)).isPresent() == false) {
			throw new ContentNotFoundException("Couldn't find TODO(" + id + ")");
		}
		TodoListEntity todoListEntity = optionalTodo.get();

        if (todoListDTO.getContent() != null) {
        	todoListEntity.setContent(todoListDTO.getContent());

            String upperTodo = todoRepository.getUpperTodo(todoListEntity.getId());
            if (upperTodo == null) {
            	upperTodo = todoListEntity.getContent();
            }
            todoListEntity.setUpperTodo(upperTodo);;
        }

        todoListEntity.setModDt(currentDate);
        todoRepository.save(todoListEntity);
        // 들어온 파라미터에 부모 아이디가 있으면 
        if (todoListDTO.getParentId() != null) {
        	// 이동 가능한지 체크 후
        	if ( !canMove(todoListEntity.getId(), todoListDTO.getParentId()) ) {
                throw new RuntimeException("Can't move TODO(" + todoListEntity.getId() +
                        ") to child TODO(" + todoListDTO.getParentId() + ")");
            }
        	moveDir(todoListEntity, todoListDTO.getParentId());
        }
        return modelMapper.map(todoListEntity, TodoListDTO.UpdateResponse.class);
	}
	
	private void moveDir (TodoListEntity todoListEntity, int id ) {

		// 이동 가능하면 root Dir에서 연결을 끊고 새로운 root에게 붙어야 됨
		directoryRepository.disconnectDir( todoListEntity.getId() );
		directoryRepository.attechNewRoot( todoListEntity.getId(), id );

		directoryRepository.findByParents(todoListEntity.getId()).stream().forEach(td -> {
            
			Optional<TodoListEntity> ot = getTodoEntity( td.getChildren() );
			
            if (ot.isPresent() == false) {
                throw new ContentNotFoundException("Couldn't find TODO(" + td.getChildren() + ")");
            }

            TodoListEntity te = ot.get();
            
            String upperTodo = todoRepository.getUpperTodo(te.getId());
            if (upperTodo == null) {
            	upperTodo = te.getContent();
            }

            te.setUpperTodo(upperTodo);

            todoRepository.save(te);
        });
    }
	private boolean canMove (int subTreeRootId, int moveTo) {
        
		List<DirectoryEntity> todoDir = directoryRepository.findByParents(subTreeRootId);
        boolean result = todoDir.stream().filter(td -> td.getId() == moveTo).count() == 0;
        
        return result;
    }
	

	@Transactional
	public DeleteResponse delete(int id) {
		// TODO Auto-generated method stub
		Optional<TodoListEntity> optTodoList = null;
        
		if ((optTodoList = getTodoEntity(id)).isPresent() == false) {
            throw new ContentNotFoundException("Couldn't find TODO(" + id + ")");
        }
		// 할일 객체를 지우고
		TodoListDTO.DeleteResponse ret = modelMapper.map(optTodoList.get(), TodoListDTO.DeleteResponse.class);

		// 할일 Directory 구조에서도 삭제
        List<DirectoryEntity> deleteTodoDirList = directoryRepository.findByParents(id);
        List<Integer> deleteIds = new ArrayList<>();

        for (DirectoryEntity  deleteTodoDir : deleteTodoDirList) {
            deleteIds.add(deleteTodoDir.getChildren());
        }

        directoryRepository.disconnectDir(deleteIds);
        todoRepository.deleteById(deleteIds);

        return ret;
	}

	@Transactional
	public UpdateResponse complete(int id) {
		// TODO Auto-generated method stub
		Optional<TodoListEntity> optionalTodo = null;
        
		if ((optionalTodo = getTodoEntity(id)).isPresent() == false) {
            throw new ContentNotFoundException("Couldn't find TODO(" + id + ")");
        }

        if (completable(id) == false) {
            throw new DependencyException("Couldn't complete TODO(" + id + ")");
        }

        TodoListEntity todoEntity = optionalTodo.get();

        Date currentDate = Calendar.getInstance().getTime();

        todoEntity.setComplDt(currentDate);
        todoEntity.setModDt(currentDate);

        todoRepository.save(todoEntity);

        return modelMapper.map(todoEntity, TodoListDTO.UpdateResponse.class);
	}	
	private boolean completable (int todoId) {
        return (todoRepository.notCompletedChildrenCnt(todoId) == 0);
    }
}
