package com.tutu.knowledge_box.ai;


import com.tutu.knowledge_box.ai.openai.completion.CompletionRequest;
import com.tutu.knowledge_box.ai.openai.embedding.Embedding;
import com.tutu.knowledge_box.ai.openai.embedding.EmbeddingRequest;

import java.util.Collections;
import java.util.List;

public class OpenAiModule {
    private static final String TOKEN = System.getenv("OPENAI_TOKEN");
    private static final OpenAiService service = new OpenAiService(TOKEN);

    /**
     * 计算
     */
    public static List<Double> getEmbedding(String message){
        EmbeddingRequest completionRequest = EmbeddingRequest.builder()
                .model("text-embedding-ada-002")
                .input(Collections.singletonList("Somebody once told me the world is gonna roll me"))
                .build();
        List<Embedding> embeddings = service.createEmbeddings(completionRequest).getData();
        return embeddings.get(0).getEmbedding();
    }

    /**
     * 询问
     */
    public static String getCompletion(String message,String questionMessage){
        StringBuilder question = new StringBuilder();
        question.append("使用提供的上下文尽可能如实回答问题，如果答案未包含在以下文本中，就说我不知道\n\n");
        question.append("内容为：\n");
        question.append(message);
        question.append("\n\n 问题是：");
        question.append(questionMessage);
        question.append(message);
        question.append("\n回答：");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(question.toString())
                .temperature(0.0)
                .maxTokens(100)
                .build();
        String result = service.createCompletion(completionRequest).getChoices().get(0).getText();
        return result;
    }
}
