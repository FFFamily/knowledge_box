package com.tutu.knowledge_box.module.knowledge;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.tutu.knowledge_box.ai.OpenAiModule;
import com.tutu.knowledge_box.enums.KnowledgeEntityEnum;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 加载知识库
 */
public class LoadKnowledgeUtil {
    private static final String KNOWLEDGE_PATH = "./chatGPT/src/main/resources/knowledge";
    private static final String ENTITY_PATH = "com.example.knowledgeEntity.";
    // EMBEDDING 后缀
    private static final String EMBEDDING_SUFFIX = "_EMBEDDING";
    /**
     * 知识库名
     */
    private static String knowledgeName = null;
    /**
     * 加载 Excel 文件
     * 解析 Excel 的框架：
     * 1，POI
     * 2，jxl
     */
    public static void loadExcel(String path){
        if (path == null || path.isBlank()){
            path = KNOWLEDGE_PATH;
        }
        try {
            File directory = new File(path);
            if (directory.isDirectory()){
                // 遍历 知识库
                for (File file : directory.listFiles()) {
                    // 拿到文件名
                    String fileName = file.getName();
                    System.out.println(fileName);
                    String fileNamePinYin = PinyinUtil.getPinyin(fileName.split("\\.")[0]).trim().replaceAll(" ","").toUpperCase();
                    knowledgeName = fileNamePinYin;
                    // 拿到文件名对应的实体对象
                    KnowledgeEntityEnum knowledgeEntityEnum = Arrays.stream(KnowledgeEntityEnum.values()).filter(knowledge -> knowledge.getValue().equals(fileName)).findFirst().get();
                    Class<?> aClass = Class.forName(ENTITY_PATH+knowledgeEntityEnum.name());
                    EasyExcel.read(file.getPath(),aClass,new DemoDataListener()).sheet().doRead();
                }
                System.out.println("知识库遍历完成");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    static class DemoDataListener implements ReadListener<Object> {


        List<String> knowledgeInfo = new ArrayList<>();

        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            System.out.println(exception.getMessage());
            ReadListener.super.onException(exception, context);
        }

        @Override
        public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {

        }

        /**
         * 这个每一条数据解析都会来调用
         */
        @Override
        public void invoke(Object data, AnalysisContext analysisContext) {
            // 限制只添加 10条，省 token 数量
            if (knowledgeInfo.size() < 10){
                knowledgeInfo.add(data.toString());
//                RedisQuery.lpush(knowledgeName,data.toString());
            }
//            System.out.println(knowledgeInfo.toString());
        }

        @Override
        public void extra(CellExtra extra, AnalysisContext context) {

        }

        /**
         * 所有数据解析完成
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//            RedisQuery.set(knowledgeName,knowledgeInfo);
            String knowledgeName_embedding = knowledgeName+EMBEDDING_SUFFIX;
            for (int i = 0; i < knowledgeInfo.size(); i++) {
                List<String> embedding = OpenAiModule.getEmbedding(knowledgeInfo.get(i)).stream().map(item -> String.valueOf(item)).collect(Collectors.toList());
//                RedisQuery.lpush(knowledgeName_embedding,embedding.toString());
            }
        }

        @Override
        public boolean hasNext(AnalysisContext context) {
            return ReadListener.super.hasNext(context);
        }
    }

    /**
     * 知识库 Item
     */
    @Data
    static class KnowledgeItem{
        private int index;
        private String message;
    }
}
