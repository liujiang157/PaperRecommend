package com.example.demo2.model;

import lombok.AllArgsConstructor;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@AllArgsConstructor
public class Category {
   private int catId;
   private String category;
   private String remarks;

    public Category() {
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
