package com.hschoi.mytodolist.repository;

public interface MyTodoRepository {
//	long countByNotCompletedDescendants (int todoId);

//    List<QTodoListEntity> getDescendants (int todoId);

    String getUpperTodo (int id);
    
    long notCompletedChildrenCnt (int todoId);
}
