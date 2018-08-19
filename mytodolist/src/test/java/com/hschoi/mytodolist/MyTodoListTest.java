package com.hschoi.mytodolist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hschoi.mytodolist.dto.TodoListDTO;
import com.hschoi.mytodolist.service.MyTodoListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTodoListTest {

	@Autowired
    private MyTodoListService myTodoListService;
	
	@Test
    public void testCreateTodo () {
        String content = "Test Todo";

        for (int i = 0; i < 100 ; i++) {
            TodoListDTO.CreateResponse createResponse = createTodo(content, null);

            Assert.assertNotNull(createResponse.getId());
            Assert.assertEquals(content, createResponse.getContent());

            TodoListDTO.FindOneResponse findOneResponse = findTodo(createResponse.getId());

            Assert.assertNotNull(findOneResponse);
            Assert.assertEquals(createResponse.getId(), findOneResponse.getId());
            Assert.assertEquals(createResponse.getContent(), findOneResponse.getContent());
        }
    }


    private TodoListDTO.CreateResponse createTodo (String content, Integer parentId) {
    	TodoListDTO.CreateRequest createRequest = new TodoListDTO.CreateRequest();
        createRequest.setContent(content);
        if (parentId != null) {
            createRequest.setParentId(parentId);
        }

        return myTodoListService.create(createRequest);
    }

    private TodoListDTO.FindOneResponse findTodo (int id) {
        return myTodoListService.get(id);
    }

}
