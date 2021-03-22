package com.paper.demo.recmmend;

/**
 * @Author: Liujiang
 * @Date: 2020/4/5 14:06
 */

public class TreeNode {
    public float val;
    int id;
    TreeNode left;
    TreeNode right;

    TreeNode(){}

    public TreeNode(int id,float similarity){
        this.id=id;
        this.val=similarity;
    }

}

