package com.tutu.knowledge_box.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KnowledgeEntityEnum {
    OccupationClassification("职业分类表.xlsx")
    ;
    private String value;
}
