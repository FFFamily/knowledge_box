package com.tutu.knowledge_box.controller;

import com.tutu.knowledge_box.entity.BaseResponse;
import com.tutu.knowledge_box.service.AIService;
import com.tutu.knowledge_box.vo.QuestionVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {
//    @Resource
//    private AIService aiService;

    /**
     * 询问问题
     */
    @PostMapping("/ask")
    public BaseResponse askQuestion(QuestionVo questionVo){
//        String result = aiService.askQuestionFromAI(questionVo.getQuestion());
//        return BaseResponse.success(result);
        return BaseResponse.SUCCESS();
    }
}
