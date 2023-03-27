package com.tutu.knowledge_box.entity.knowledge;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 职业分类表实体
 */
@Getter
@Setter
@EqualsAndHashCode
public class OccupationClassification {
    private String type1;
    private String type2;
    private String type3;
    private String code;
    private String docs;
    private String type;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (type1 != null){
            stringBuilder.append("大分类是'" + type1 + '\'');
        }
        if (type2 != null){
            stringBuilder.append("小分类是'" + type2 + '\'');
        }
        if (type3 != null){
            stringBuilder.append("小分类是'" + type3 + '\'');
        }
        if (code != null){
            stringBuilder.append("代码是'" + code + '\'');
        }
        if (docs != null){
            stringBuilder.append("细类是'" + docs + '\'');
        }
        if (type != null){
            stringBuilder.append("类别是'" + type + '\'');
        }
        return stringBuilder.toString();
    }
}
