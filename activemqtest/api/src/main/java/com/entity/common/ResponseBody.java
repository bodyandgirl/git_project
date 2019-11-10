package com.entity.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder(toBuilder = true)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private Integer totalCount;
    private Integer pageNo;
    private Integer pageSize;
}
