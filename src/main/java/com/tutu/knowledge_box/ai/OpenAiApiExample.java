package com.tutu.knowledge_box.ai;




import com.tutu.knowledge_box.ai.openai.embedding.EmbeddingRequest;

import java.util.Collections;

class OpenAiApiExample {
    public static void main(String... args) {
        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(token);
        System.out.println("\nCreating completion...");
        EmbeddingRequest completionRequest = EmbeddingRequest.builder()
                .model("text-embedding-ada-002")
                .input(Collections.singletonList("Somebody once told me the world is gonna roll me"))
                .build();
        service.createEmbeddings(completionRequest).getData().forEach(System.out::println);
    }
}
