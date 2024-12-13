package com.mySpring.myapp.notice.vo;

import java.util.Date;

public class ArticleVO {
    private int articleNO;
    private int parentNO;
    private String title;
    private String content;
    private String imageFileName;
    private Date writeDate;

    // Getters and Setters
    public int getArticleNO() {
        return articleNO;
    }

    public void setArticleNO(int articleNO) {
        this.articleNO = articleNO;
    }

    public int getParentNO() {
        return parentNO;
    }

    public void setParentNO(int parentNO) {
        this.parentNO = parentNO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }
}
