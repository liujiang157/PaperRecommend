package com.paper.demo.recmmend;

import com.paper.demo.entity.bo.BrowerRecord;
import com.paper.demo.entity.bo.DownloadRecord;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

/**
 * @Author: Liujiang
 * @Date: 2020/4/5 14:10
 */

public class UserRating {
    private final static float PLAY_SCORE=1f;
    private final static float DOWNLOAD_SCORE=2f;
    private final static float COLLECTION_SCORE=5f;
    private final static float MAX_SCORE=10f;
    private final static int SONG_ID_SET_KEY=0;

    /**
     * 构建用户频率矩阵来近似用户评分，对于某些系统而言，我们是不可能获取到用户对某些项目的评分的，但是我们可以利用用户的
     * 行为习惯来反映用户的“评分”，比如一个用户常常收听某一首歌，那么我们可以推断该用户喜欢该论文的可能性很大.
     * 总分10分，主动浏览一次1分，下载2分，收藏5分，如果超过10分，按10分计算.
     * @param userIdList
     * 用户Id列表
     * @param paperIdList
     * 论文Id列表
     * @param downloadList
     * 用户的下载记录列表
     * @param playList
     * 用户的浏览记录列表
     * @return
     * 用户Id-论文Id 频率矩阵
     */
    public static Map<Integer, float[]> getFrequencyMatrix(List<Integer> userIdList, final List<Integer> paperIdList,
                                                           List<DownloadRecord> downloadList, List<BrowerRecord> playList) {
        // TODO Auto-generated method stub
        final Map<Integer,float[]> user2paperRatingMatrix=new HashMap<Integer, float[]>();
        final int paperLen=paperIdList.size();
        //获取用户-论文 下载映射
        final Map<Integer, Map<Integer, Set<Integer>>> userId2paperIdDownloadMap=getUserId2paperIdRecordMap(downloadList,false);
        //获取用户-论文-次数 浏览映射
        final Map<Integer, Map<Integer, Set<Integer>>> userId2paperIdPlayMap=getUserId2paperIdRecordMap(playList,true);

        userIdList.forEach(new Consumer<Integer>() {

            public void accept(Integer userId) {
                // TODO Auto-generated method stub
                float[] curUserRatingArray=new float[paperLen];
                int paperIndex=0;
                //处理每一首论文
                for(Integer paperId:paperIdList) {
                    /**
                     * 处理下载，这里不考虑下载次数
                     */
                    if(userId2paperIdDownloadMap.get(userId)!=null && userId2paperIdDownloadMap.get(userId).get(SONG_ID_SET_KEY).contains(paperId)) {
                        //当前用户下载过的论文
                        curUserRatingArray[paperIndex]+=COLLECTION_SCORE;
                    }


                    /**
                     * 处理浏览，考虑浏览次数
                     */
                    if(userId2paperIdPlayMap.get(userId)!=null && userId2paperIdPlayMap.get(userId).get(SONG_ID_SET_KEY).contains(paperId)) {
                        //当前用户浏览过的论文
                        int count=userId2paperIdPlayMap.get(userId).get(paperId).iterator().next();
                        curUserRatingArray[paperIndex]+=PLAY_SCORE + count;
                    }

                    /**
                     * 处理最大得分，超过最大得分，记为最大得分
                     */
                    if(curUserRatingArray[paperIndex]>MAX_SCORE) {
                        curUserRatingArray[paperIndex]=MAX_SCORE;
                    }
                    //处理下一首歌
                    paperIndex++;
                }
                //处理完一个用户
                user2paperRatingMatrix.put(userId, curUserRatingArray);
            }

        });
        return user2paperRatingMatrix;
    }

    /**
     * 获取用户Id - 论文Id 的映射Map
     * @param recordList
     * 包含userId，paperId的记录列表
     * @param isCount
     * 是否需要计数。如果true，则Integer[1]存放计数。
     * @return
     * 两层Map
     * 第一层Map<Integer,Map> 每个userId拥有一个自己的Map：
     * userId,userSetMap
     *
     * 第二层Map<Integer,Set> 用户自己的Map里面存放两个东西：
     * （1）为每首论文计数paperId,CountSet；
     * （2）存放出现过的论文paperSetFlay,PaperIdSet：
     */
    private static <T> Map<Integer, Map<Integer, Set<Integer>>> getUserId2paperIdRecordMap(final List<T> recordList, final boolean isCount) {
        // TODO Auto-generated method stub
        final Map<Integer, Map<Integer, Set<Integer>>> userId2paperIdRecordMap=new HashMap<Integer, Map<Integer, Set<Integer>>>();

        recordList.forEach(new Consumer<T>() {

            public void accept(T t) {
                // TODO Auto-generated method stub
                try {
                    //利用反射获和泛型获取不同类型表的相同属性
                    Field userIdField=t.getClass().getDeclaredField("userId");
                    Field paperIdField=t.getClass().getDeclaredField("paperId");
                    userIdField.setAccessible(true);
                    paperIdField.setAccessible(true);
                    int userId=userIdField.getInt(t);
                    int paperId=paperIdField.getInt(t);
                    //不需要计数
                    if(!isCount) {
                        //map外层的userId已经存在
                        if(userId2paperIdRecordMap.containsKey(userId)) {
                            //获取当前用户的记录集合Map
                            Map<Integer, Set<Integer>> curRecordSetMap=userId2paperIdRecordMap.get(userId);
                            //将当前论文添加到当前用户的记录集合中
                            curRecordSetMap.get(SONG_ID_SET_KEY).add(paperId);
                        }else {
                            Map<Integer, Set<Integer>> curRecordSetMap=new HashMap<Integer, Set<Integer>>();
                            //创建记录论文Id的集合
                            Set<Integer> curPaperIdSet=new HashSet<Integer>();
                            curPaperIdSet.add(paperId);
                            curRecordSetMap.put(SONG_ID_SET_KEY, curPaperIdSet);
                            userId2paperIdRecordMap.put(userId, curRecordSetMap);
                        }
                    }else {
                        //map外层的userId已经存在
                        if(userId2paperIdRecordMap.containsKey(userId)) {
                            //获取当前用户的记录集合Map
                            Map<Integer, Set<Integer>> curRecordSetMap=userId2paperIdRecordMap.get(userId);
                            //将当前论文添加到当前用户的记录集合中
                            curRecordSetMap.get(SONG_ID_SET_KEY).add(paperId);

                            //计数
                            count(paperId,curRecordSetMap);

                        }else {
                            Map<Integer, Set<Integer>> curRecordSetMap=new HashMap<Integer, Set<Integer>>();
                            //创建记录论文Id的集合
                            Set<Integer> curPaperIdSet=new HashSet<Integer>();
                            curPaperIdSet.add(paperId);
                            curRecordSetMap.put(SONG_ID_SET_KEY, curPaperIdSet);
                            userId2paperIdRecordMap.put(userId, curRecordSetMap);

                            //计数
                            count(paperId,curRecordSetMap);

                        }
                    }

                }catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            private void count(int paperId, Map<Integer, Set<Integer>> curRecordSetMap) {
                // TODO Auto-generated method stub
                /**
                 * 计数,如果Map<paperId,count>已经存在，则直接计数+1
                 */
                if(curRecordSetMap.containsKey(paperId)) {
                    //获取当前用户论文的计数集合(只有一个元素)
                    Set<Integer> curCountSet=curRecordSetMap.get(paperId);
                    int cnt=curCountSet.iterator().next()+1;
                    curCountSet.clear();
                    curCountSet.add(cnt);
                }else {
                    Set<Integer> curCountSet=new HashSet<Integer>();
                    curCountSet.add(1);
                    curRecordSetMap.put(paperId, curCountSet);
                }
            }


        });
        return userId2paperIdRecordMap;
    }


}
