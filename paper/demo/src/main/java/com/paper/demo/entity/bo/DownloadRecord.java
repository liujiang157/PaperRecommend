package com.paper.demo.entity.bo;

import java.util.Date;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
public class DownloadRecord {
    private int downloadId;
    private int userId;
    private int paperId;
    private Date downloadTime;

    public DownloadRecord() {
    }

    public DownloadRecord(int userId, int songId) {
        this.userId = userId;
        this.paperId = songId;
        this.downloadTime =new Date();
    }

    public int getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSongId() {
        return paperId;
    }

    public void setSongId(int songId) {
        this.paperId = songId;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }


}
