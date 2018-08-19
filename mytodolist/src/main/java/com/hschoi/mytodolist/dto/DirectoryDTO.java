package com.hschoi.mytodolist.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
* <pre>
* com.hschoi.myTodoList.dto
* </pre>
*
* Desc 	   : TodoList는 참조 가능한 Directory 구조를 가지고 있음 Root의 부모 일정과 하위의 자식 할일들로 정의
* @Company : eBay Japan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 18.
* @Version :
*/
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectoryDTO {
	@JsonProperty("parents")
    private int parents;

    @JsonProperty("children")
    private int children;
}
