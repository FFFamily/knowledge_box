package com.tutu.knowledge_box.service;

import cn.hutool.json.JSONUtil;
import com.tutu.knowledge_box.ai.OpenAiModule;
import com.tutu.knowledge_box.module.knowledge.Item;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AIService {
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 向GPT询问问题
     */
    public String askQuestionFromAI(String question) {
        System.out.println("您询问的问题是：" + question);
        List<BigDecimal> embedding = OpenAiModule.getEmbedding(question).stream().map(item -> BigDecimal.valueOf(item)).collect(Collectors.toList());
        // 现在默认查询职业分类的知识库
        List<String> zhiyefenleibiaoEmbedding = redisTemplate.opsForList().range("ZHIYEFENLEIBIAO_EMBEDDING",0,-1);
        List<List<BigDecimal>> collect = zhiyefenleibiaoEmbedding.stream().map(JSONUtil::parseArray)
                .map(item -> item.stream().map(temp -> new BigDecimal(temp.toString())).collect(Collectors.toList()))
                .collect(Collectors.toList());
//        BigDecimal dotProduct = BigDecimal.ZERO;
        List<Item> res = new ArrayList<>();
        for (int i = 0; i < collect.size(); i++) {
            List<BigDecimal> doubles = collect.get(i);
            BigDecimal dotProduct = BigDecimal.ZERO;
            BigDecimal norm1 =  BigDecimal.ZERO;
            BigDecimal norm2 =  BigDecimal.ZERO;
            for (int j = 0; j < doubles.size(); j++) {
                BigDecimal multiply = doubles.get(j).multiply(embedding.get(j));
                dotProduct = dotProduct.add(multiply);
                norm1 = norm1.add(doubles.get(j).multiply(doubles.get(j)));
                norm2 = norm2.add(embedding.get(j).multiply(embedding.get(j)));
            }
            BigDecimal similarity = dotProduct.divide(BigDecimal.valueOf((Math.sqrt(norm1.doubleValue()) * Math.sqrt(norm2.doubleValue()))),20, RoundingMode.HALF_UP);
            res.add(new Item(i,similarity));
        }
        res.forEach(System.out::println);
        List<Item> list = res.stream().filter(Objects::nonNull).sorted((a, b) -> b.getDot().compareTo(a.getDot())).filter(item -> item.getDot() != null && item.getDot().compareTo(BigDecimal.valueOf(0.8)) > 0).collect(Collectors.toList());

        System.out.println("-----------------");
        list.forEach(System.out::println);
        List<String> zhiyefenleibiao = redisTemplate.opsForList().range("ZHIYEFENLEIBIAO", 0, -1);
        List<String> result = new ArrayList<>();

        for (int i = 0 ; i < 5 ; i ++){
            result.add(zhiyefenleibiao.get(list.get(i).getIndex()));
        }
        result.forEach(System.out::println);
        System.out.println("我正在思考，请骚等");
        String join = String.join("", zhiyefenleibiao.subList(0,20));
        System.out.println(join);
        String completion = OpenAiModule.getCompletion(join, question);
        System.out.println(completion);
        return null;
    }
}
