package com.tutu.knowledge_box.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {
    private int code;
    private String msg;

    public static BaseResponse SUCCESS(){
        return new BaseResponse(200,"成功");
    }

    public static BaseResponse success(String message){
        return new BaseResponse(200,message);
    }
}
