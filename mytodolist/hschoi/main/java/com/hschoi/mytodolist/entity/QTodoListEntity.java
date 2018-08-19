package com.hschoi.mytodolist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodoListEntity is a Querydsl query type for TodoListEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTodoListEntity extends EntityPathBase<TodoListEntity> {

    private static final long serialVersionUID = 240683563L;

    public static final QTodoListEntity todoListEntity = new QTodoListEntity("todoListEntity");

    public final ListPath<DirectoryEntity, QDirectoryEntity> children = this.<DirectoryEntity, QDirectoryEntity>createList("children", DirectoryEntity.class, QDirectoryEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> complDt = createDateTime("complDt", java.util.Date.class);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> modDt = createDateTime("modDt", java.util.Date.class);

    public final ListPath<DirectoryEntity, QDirectoryEntity> parents = this.<DirectoryEntity, QDirectoryEntity>createList("parents", DirectoryEntity.class, QDirectoryEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> regDt = createDateTime("regDt", java.util.Date.class);

    public final StringPath upperTodo = createString("upperTodo");

    public QTodoListEntity(String variable) {
        super(TodoListEntity.class, forVariable(variable));
    }

    public QTodoListEntity(Path<? extends TodoListEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTodoListEntity(PathMetadata metadata) {
        super(TodoListEntity.class, metadata);
    }

}

