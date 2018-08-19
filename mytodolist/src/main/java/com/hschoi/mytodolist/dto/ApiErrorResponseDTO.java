package com.hschoi.mytodolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* <pre>
* com.hschoi.myTodoList.dto
* </pre>
*
* Desc 	   : API Error Response 결과를 담을 DTO, Error는 Http 응답 코드와 메시지로 정의
* @Company : eBay Japan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 18.
* @Version :
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponseDTO {
	private int code;
    private String message;
}
