package com.paper.demo.entity.bo;

import java.util.Date;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */

public class BrowerRecord {
    private int browerId;
    private int userId;
    private int paperId;
    private Date browseTime;

    public BrowerRecord(int userId, int paperId) {
        this.userId = userId;
        this.paperId = paperId;
    }

    public BrowerRecord(int browerId, int userId, int paperId, Date browseTime) {
        this.browerId = browerId;
        this.userId = userId;
        this.paperId = paperId;
        this.browseTime = browseTime;
    }

    public BrowerRecord() {
    }

    public int getBrowerId() {
        return browerId;
    }

    public void setBrowerId(int browerId) {
        this.browerId = browerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }
}
