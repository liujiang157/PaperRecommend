package com.paper.demo.entity.vo;

import com.paper.demo.entity.bo.Paper;

import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
public class Top5PaperVo {
    private int catId;
    private String category;
    private List<Paper> paperList;

    public Top5PaperVo(int catId, String category, List<Paper> paperList) {
        this.catId = catId;
        this.category = category;
        this.paperList = paperList;
    }

    public Top5PaperVo() {
    }

    public int getCatId() {
        return this.catId;
    }

    public String getCategory() {
        return this.category;
    }

    public List<Paper> getPaperList() {
        return this.paperList;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPaperList(List<Paper> paperList) {
        this.paperList = paperList;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Top5PaperVo)) return false;
        final Top5PaperVo other = (Top5PaperVo) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getCatId() != other.getCatId()) return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$paperList = this.getPaperList();
        final Object other$paperList = other.getPaperList();
        if (this$paperList == null ? other$paperList != null : !this$paperList.equals(other$paperList)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Top5PaperVo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getCatId();
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $paperList = this.getPaperList();
        result = result * PRIME + ($paperList == null ? 43 : $paperList.hashCode());
        return result;
    }

    public String toString() {
        return "Top5PaperVo(catId=" + this.getCatId() + ", category=" + this.getCategory() + ", paperList=" + this.getPaperList() + ")";
    }
}
