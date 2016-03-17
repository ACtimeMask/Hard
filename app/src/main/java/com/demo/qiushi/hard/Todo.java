package com.demo.qiushi.hard;

/**
 * Created by Administrator on 2016/3/14.
 */
public class Todo {
    private String title,content;
    public Todo(String title, String content){
        this.title=title;
        this.content=content;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setContent(String content){
        this.content=content;
    }
}