package com.hschoi.mytodolist.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* <pre>
* com.hschoi.todolist.dto
*   |_ TodoListDTO.java
* </pre>
*
* Desc : TodoList DTO ����
* 
* @Company : ebayJapan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 16. ���� 9:54:46
* @Version :
*/
public class TodoListDTO {

	@Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class RequestBase {
		@JsonProperty("parent_id")
        private Integer parentId;
		@JsonProperty("content")
        private String content;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class CreateRequest extends RequestBase{
    	//������ Ÿ��, 
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateRequest extends RequestBase {
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class ResponseBase {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("content")
        private String content;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class CreateResponse extends ResponseBase {
        @JsonProperty("reg_dt")
        private Date regDt;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class UpdateResponse extends ResponseBase {
        @JsonProperty("mod_dt")
        private Date modDt;

        @JsonProperty("compl_dt")
        private Date complDt;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class DeleteResponse extends ResponseBase {
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class SearchResponseBase extends ResponseBase {
    	@JsonProperty("reg_dt")
        private Date regDt;

    	@JsonProperty("mod_dt")
        private Date modDt;

        @JsonProperty("compl_dt")
        private Date complDt;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class FindOneResponse extends SearchResponseBase {
        @JsonProperty("upper_todo")
        private String upperTodo;

        @JsonProperty("parents")
        private List<DirectoryDTO> parents;

        @JsonProperty("children")
        private List<DirectoryDTO> children;
    }

    @Data 
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @EqualsAndHashCode(callSuper = false)
    public static class FindAllResponse extends SearchResponseBase {
        @JsonProperty("upper_todo")
        private String upperTodo;
    }
}
