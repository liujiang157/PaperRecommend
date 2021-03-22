package com.example.demo2.utils;

public class Const {

    public final static String accessKey = "TiTA_El6_eUVW6Dnx4mOoh7zBLFsPCnClwQQANsk"; //七牛云ack

    public final static String secretKey = "dD0QTaE84XO5ywVx3cKdChKRzi0ApOroOrwG_W69"; //七牛云sck

    public final static String bucket = "liujiangpaperrec";                               //七牛云空间名

    public final static String qiniupath = "http://img.aixuxi.cn/";

    /**
     * 用于记录，两张个性化推荐列表，isFromA为true表示，从表A中读取数据;
     * 否则从表B中读取.
     * 每天早上6点，两张表交替更新
     */
    public static volatile boolean isFromA=true;

    /**
     * 更新个性化推荐列表的时间间隔(这里每天更新一次)
     */
    public static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    /**
     * 更新开始的时间：时
     */
    public static final int START_HOUR = 18;

    /**
     * 更新开始的时间：分
     */
    public static final int START_MINUTE = 0;

    /**
     * 更新开始的时间：秒
     */
    public static final int START_SECOND = 0;

    /**
     * 更新是否从明天开始
     */
    public static final boolean IS_START_TOMORROW = false;

    /**
     * KNN k值
     * 目前系统用户很少
     */
    public static final int K = 3;

    /**
     * 基于最近邻用户的协同过滤给用户推荐歌曲的数量 n值
     * 歌曲很少
     */
    public static final int N = 12;





}
