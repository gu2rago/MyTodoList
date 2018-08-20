# MyTodoList
simple TodoList Management

# MyTodoList 등록

 		title			     id			    parents			    child

		회사일			    1                         1                              1  	
		  |	
		  |- 복사			     2                         1                              2
		  |- 코딩			     3                         1                              3
		      |
		      |- 디버깅                 4                         3                              4
		집안일                          5                         2                              5


위와 같이 Todo에는 각각의 고유한 id를 가지고 할일에 대한 내용을 저장 할 수 있다.
Todo는 Have a 관계를 갖을 수 있다. 
회사일이라는 하나의 부모 Todo에는 연관성이 있는 하위 Todo를 갖을 수 있는 관계이다 (1:N).
이는 Directory 구조로 표현 할 수 있다.

# Todo 수정
 - Todo의 유니크한 id를 파라미터로 받아서 Todo 내용을 수정 한다.
 - parent_id가 있을 경우 이동가능한지 체크하여 이동 가능 할 경우, 파라미터로 받은 parent_id 하위로 Todo을 수정한다.
	* 이동가능한지 체크 
	파라미터로 받은 parent_id의 directory를 조회하여, 자신의 id와 같은것이 있는지 filter, count가 0이면 이동 가능

# Todo 삭제
 - 파라미터로 받은 Todo id를 이용하여 자신이 달린 directory를 조회 후
 - directory 삭제 -> Todo list 삭제

# Todo 완료
 - Todo의 유니크한 id를 파라미터로 받아서 Todo 내용을 완료처리 한다.
 - 파라미터로 받은 Todo id를 이용하여 자신이 달린 directory를 조회 후
 - TodoEntity의 완료일을 관리하는 변수가 Null일 경우 완료처리 한다. 

# 개발환경
 Java 8, Spring Boot 1.5.16.BUILD-SNAPSHOT,gradle, JPA

# Build
 Mac, Linux : ./gradlew clean build
 
# Comment
 Front는 구현하지 못하고 Advenced Rest Client 를 이용하였습니다. 
