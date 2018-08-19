package com.hschoi.mytodolist.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.hschoi.mytodolist.entity.QDirectoryEntity;
import com.hschoi.mytodolist.entity.QTodoListEntity;
import com.hschoi.mytodolist.entity.TodoListEntity;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TodoRepositoryImpl extends QueryDslRepositorySupport {
	
	public TodoRepositoryImpl() {
		super(TodoListEntity.class);
		// TODO Auto-generated constructor stub
	}

	public String getUpperTodo (int todoId) {
        EntityManager em = getEntityManager();

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT 	GROUP_CONCAT( CONCAT('@', PARENTS) SEPARATOR ',') AS UPPER_TODO \n");
        sb.append("FROM 	(\n"                                                                                     );
        sb.append("			SELECT TD.CONTENT, DI.PARENTS, DI.CHILDREN \n"                                                         );
        sb.append("			  FROM TODO TD \n"                                                                       );
        sb.append("			 INNER JOIN DIRECTORY DI ON DI.CHILDREN = ?1 AND DI.PARENTS <> DI.CHILDREN\n"       );
        sb.append("			 WHERE TD.ID = ?2\n"                                                                     );
        sb.append("			 ORDER BY DI.PARENTS\n"                                                                  );
        sb.append("			) TMP\n"                                                                                 );
        sb.append("GROUP BY CONTENT\n"                                                                                 );

        Query query = em.createNativeQuery(sb.toString());

        query.setParameter(1, todoId);
        query.setParameter(2, todoId);

        String ret = null;
        List<Object> resultList = query.getResultList();

        if (resultList.size() > 0) {
            ret = (String) resultList.get(0);
        }
        return ret;
    }
	
	public List<TodoListEntity> getChildren (int todoListId) {
		QTodoListEntity todoListEntity = QTodoListEntity.todoListEntity;
		QDirectoryEntity dirEntity = QDirectoryEntity.directoryEntity;

        JPQLQuery query = from(new EntityPath[] {todoListEntity, dirEntity});

        query.where(dirEntity.parents.eq(todoListId));
        query.where(dirEntity.children.eq(todoListEntity.id));
        query.where(dirEntity.children.ne(todoListId));

        return query.select(todoListEntity).fetch();
    }
	
	public long notCompletedChildrenCnt (int todoId) {
        
		QTodoListEntity todoEntity = QTodoListEntity.todoListEntity;
        QDirectoryEntity dirEntity = QDirectoryEntity.directoryEntity;

        JPQLQuery query = from(new EntityPath[] {todoEntity, dirEntity});

        query.where(dirEntity.parents.eq(todoId));
        query.where(dirEntity.children.eq(todoEntity.id));
        query.where(dirEntity.children.ne(todoId));

        query.where(todoEntity.complDt.isNull());

        return (Long) query.select(todoEntity.count()).fetchFirst();
    }
	
}
