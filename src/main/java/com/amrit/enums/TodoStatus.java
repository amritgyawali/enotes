package com.amrit.enums;

import lombok.Getter;
import lombok.Setter;


@Getter
public enum TodoStatus {
    NOT_STARTED(1,"Not Started"), IN_PROGRESS(2,"IN PROGRESS"), COMPLETED(3,"COMPLETED");

    private Integer id;
    private final String name;

    TodoStatus(int id, String name) {
        this.id=id;
        this.name=name;
    }


    public void setId(int id){
        this.id=id;
    }

    public String getName(String name){
        return name;
    }

    public void setName(String name){
        this.setName(name);
    }
}
