package com.hschoi.mytodolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hschoi.mytodolist.entity.DirectoryEntity;

/**
* <pre>
* com.hschoi.myTodoList.repository
* </pre>
*
* Desc 	   : Directory 기능 정의
* @Company : eBay Japan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 18.
* @Version :
*/
public interface DirectoryRepository extends JpaRepository<DirectoryEntity, Integer>, MyDirectoryRepository {
	 List<DirectoryEntity> findByParents (int parents);
	 void disconnectDir (List<Integer> childrenList);
}
