package com.hschoi.mytodolist.configuration;

import java.lang.annotation.Annotation;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.hschoi.mytodolist.dto.ApiErrorResponseDTO;
import com.hschoi.mytodolist.dto.ApiResponseDTO;
import com.hschoi.mytodolist.exception.ContentNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiResponseHandler implements ResponseBodyAdvice<Object> {
    @ExceptionHandler(
            value = {
                    ContentNotFoundException.class
            }
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponseDTO handleNotFoundException (final Exception e) {
        log.error(e.getMessage(), e);

        return new ApiErrorResponseDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(
            value = {
//                    InvalidMoveTargetException.class,
//                    TodoDependencyException.class,
//                    ParentCompletedException.class,
//                    InvalidTodoReqeustException.class
            }
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponseDTO handleBadRequestException (final Exception e) {
        log.error(e.getMessage(), e);

        return new ApiErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        HttpStatus httpStatus = HttpStatus.OK;

        Object ret = null;
        if (body instanceof ApiResponseDTO) {
        	ApiResponseDTO apiResponse = (ApiResponseDTO) body;

            try {
                httpStatus = HttpStatus.valueOf(apiResponse.getCode());
                response.setStatusCode(httpStatus);

                ret = response;
            }
            catch (IllegalArgumentException iae) {}
        }
        else {
            for (Annotation annotation : returnType.getMethodAnnotations()) {
                if (annotation instanceof ResponseStatus) {
                    ResponseStatus responseStatus = (ResponseStatus) annotation;
                    httpStatus = responseStatus.value();
                    break;
                }
            }

            ret = new ApiResponseDTO(httpStatus.value(), httpStatus.getReasonPhrase(), body);
        }

        return ret;
    }
}
