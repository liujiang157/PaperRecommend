package com.paper.demo.recmmend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author: Liujiang
 * @Date: 2020/4/5 14:23
 */

public class CollaborativeFiltering {

    /**
     * 基于最近邻用户产生协同过滤的推荐结果
     * @param userIdList
     * 用户Id列表
     * @param userKNNMatrix
     * 用户KNN矩阵
     * @param user2paperRatingMatrix
     * 用户论文“评分”矩阵
     * @param paperIdList
     * 论文Id列表
     * @param n
     * 推荐的前n首论文
     * @return
     * 用户论文推荐结果矩阵.userId,[recPaperId1,recPaperId2...recPaperIdn]
     */
    public static Map<Integer, Integer[]> userKNNBasedCF(List<Integer> userIdList,
                                                         final Map<Integer, Integer[]> userKNNMatrix, final Map<Integer, float[]> user2paperRatingMatrix,
                                                         final List<Integer> paperIdList, final int n) {
        final Map<Integer, Integer[]> userpaperRecMatrix=new HashMap<Integer, Integer[]>();
        userIdList.forEach(new Consumer<Integer>() {
            public void accept(Integer curUserId) {
                Integer[] knnIdArray=userKNNMatrix.get(curUserId);
                float[] curUserRatings=user2paperRatingMatrix.get(curUserId);
                MininumHeap mininumHeap=new MininumHeap(n);
                for(int i=0;i<curUserRatings.length;i++) {
                    if(curUserRatings[i]<0.01f) {
                        for(int knnIndex=0;knnIndex<knnIdArray.length;knnIndex++) {
                            int knnId=knnIdArray[knnIndex];
                            float[] knnUserRatings=user2paperRatingMatrix.get(knnId);
                            curUserRatings[i]+=knnUserRatings[i];
                        }
                        curUserRatings[i]/=knnIdArray.length;
                        int curPaperId=paperIdList.get(i);
                        mininumHeap.addElement(new TreeNode(curPaperId,curUserRatings[i]));
                    }
                }
                int trueNumber=n;
                if(mininumHeap.getCurHeapSize()<n) {
                    trueNumber=mininumHeap.getCurHeapSize();
                }
                Integer[] curUserRecPaperId=new Integer[trueNumber];
                for(int i=0;i<trueNumber;i++) {
                    int recPaperId=mininumHeap.getArray()[i].id;
                    curUserRecPaperId[i]=recPaperId;
                }
                userpaperRecMatrix.put(curUserId, curUserRecPaperId);

            }

        });
        return userpaperRecMatrix;
    }

}

