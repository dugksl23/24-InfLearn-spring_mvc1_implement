package com.example.study.web.frontController;

import jakarta.persistence.Entity;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Columns;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class ModelView {

    // 논리뷰
    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    //객체 생성시 생성자의 매개변수로 논리 뷰 이름전달.
    public ModelView(String viewName) {
        this.viewName = viewName;
    }


}
