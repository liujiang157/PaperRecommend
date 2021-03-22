package com.paper.demo.recmmend;


import com.paper.demo.entity.bo.BrowerRecord;
import com.paper.demo.entity.bo.DownloadRecord;
import com.paper.demo.service.*;
import com.paper.demo.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Liujiang
 * @Date: 2020/4/5 14:21
 */
@Component
public class DailyAction implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private PersonalRecService personalRecService;
    @Autowired
    private RecordDownloadService recordDownloadService;
    @Autowired
    private RecordBrowerService recordPlayService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaperService paperService;
    /**
     * 是否第一次初始化
     */
    private static volatile boolean isFirtTimeInit=true;

    public void onApplicationEvent(ApplicationReadyEvent arg0) {
        System.out.println("###-----Spring 容器加载完毕_-_-----###");
        init(arg0);
    }

    private void init(ApplicationReadyEvent arg0) {
        if(isFirtTimeInit) {
            System.out.println("###-----开始Listener_-_-----###");
            ConfigurableApplicationContext applicationContext = arg0.getApplicationContext();
            userService = applicationContext.getBean(UserService.class);
            paperService = applicationContext.getBean(PaperService.class);
            recordPlayService = applicationContext.getBean(RecordBrowerService.class);
            recordDownloadService = applicationContext.getBean(RecordDownloadService.class);
            personalRecService =  applicationContext.getBean(PersonalRecService.class);
            Listener listener=new Listener(new TimerTask() {

                @Override
                public void run() {
                    System.out.println("------------开始执行任务-------------");
                    try {
                        //等待10s再开始执行任务
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //更换推荐列表
                    Const.isFromA=!Const.isFromA;
                    //用户-论文 推荐列表
                    Map<Integer, Integer[]> user2paperRecMatrix=new HashMap<Integer, Integer[]>();
                    //获取用户的下载记录
                    List<DownloadRecord> downloadList=recordDownloadService.getAllRecords();
                    //获取用户的浏览记录
                    List<BrowerRecord> playList=recordPlayService.getAllRecords();
                    //获取用户
                    List<Integer> userIdList=userService.getAllUserIdRecords();
                    //获取论文
                    List<Integer> paperIdList=paperService.getAllPaperIdRecords();
                    //用户-论文 “评分”矩阵
                    Map<Integer, float[]> user2paperRatingMatrix=UserRating.getFrequencyMatrix(userIdList,paperIdList,
                            downloadList,playList);
                    //用户相似性计算，获取用户的k个近邻用户
                    Map<Integer, Integer[]> userKNNMatrix= UserKNN.getKNN(userIdList,user2paperRatingMatrix, Const.K);
                    //基于用户相似性的协同过滤
                    user2paperRecMatrix=CollaborativeFiltering.userKNNBasedCF(userIdList,userKNNMatrix,
                            user2paperRatingMatrix,paperIdList, Const.N);
                    System.out.println("------------执行任务完成-------------");
                    if(Const.isFromA) {
                        //向B中更新写数据
                        personalRecService.updatePersonalRecIntoB(user2paperRecMatrix);
                    }else {
                        //向A中更新写数据
                        personalRecService.updatePersonalRecIntoA(user2paperRecMatrix);
                    }
                }

            });
            //开始执行监听
            listener.listen(Const.START_HOUR, Const.START_MINUTE,
                    Const.START_SECOND, Const.PERIOD_DAY, Const.IS_START_TOMORROW);
        }
        isFirtTimeInit=false;
    }

}

