package com.paper.demo.entity.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Document(indexName = "paper")
public class Paper implements Serializable {

    @Id
    private int paperId;

    private int catId;

    private String url;

    @Field(analyzer = "ik_max_word")
    private String title;

    private String remarks;

    private Date createTime;


    public Paper() {
    }

    public Paper(int paperId, int catId, String url, String title) {
        this.paperId = paperId;
        this.catId = catId;
        this.url = url;
        this.title = title;
    }

    public Paper(int paperId, int catId, String url, String title, String remarks, Date createTime) {
        this.paperId = paperId;
        this.catId = catId;
        this.url = url;
        this.title = title;
        this.remarks = remarks;
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
