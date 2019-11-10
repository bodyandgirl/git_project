package com.entity.test;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
public class User implements Serializable {
    private String name;
    private String nickName ;
    @NotNull(message = "等级编号不能为空")
    private String classNo;

}
