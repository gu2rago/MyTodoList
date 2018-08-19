package com.hschoi.mytodolist.repository;

/**
* <pre>
* com.hschoi.myTodoList.repository
* </pre>
*
* Desc 	   : Native Query를 위한 Repository 기능 정의
* @Company : eBay Japan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 18.
* @Version :
*/
public interface MyDirectoryRepository {
	// createDirector
	void createDirectoryById (int rootId, int childId);
	//disconnectDirectory
	void disconnectDir (int rootId);
    // attechNewRoot
	void attechNewRoot (int ordRootId, int NewRootId);
}
