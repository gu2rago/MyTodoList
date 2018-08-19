package com.hschoi.mytodolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
* <pre>
* com.hschoi.myTodoList.dto
* </pre>
*
* Desc 	   : API Response 결과를 담을 DTO
* @Company : eBay Japan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 18.
* @Version :
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO<T> {
	private int code;
    private String message;
    private T data;
}
