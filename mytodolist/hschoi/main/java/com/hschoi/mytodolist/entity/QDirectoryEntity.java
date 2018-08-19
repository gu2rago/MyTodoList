package com.hschoi.mytodolist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDirectoryEntity is a Querydsl query type for DirectoryEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDirectoryEntity extends EntityPathBase<DirectoryEntity> {

    private static final long serialVersionUID = -916014580L;

    public static final QDirectoryEntity directoryEntity = new QDirectoryEntity("directoryEntity");

    public final NumberPath<Integer> children = createNumber("children", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> parents = createNumber("parents", Integer.class);

    public QDirectoryEntity(String variable) {
        super(DirectoryEntity.class, forVariable(variable));
    }

    public QDirectoryEntity(Path<? extends DirectoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDirectoryEntity(PathMetadata metadata) {
        super(DirectoryEntity.class, metadata);
    }

}

