package com.antiless.daemon.config;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 默认前台服务样式
 */
public class ForegroundNotification implements Serializable {
    private int id;
    private String title;
    private String description;
    private int iconRes;
    private ForegroundNotificationClickListener foregroundNotificationClickListener;
    private ForegroundNotification(){

    }
    public ForegroundNotification(int id, String title, String description, int iconRes, ForegroundNotificationClickListener foregroundNotificationClickListener) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconRes = iconRes;
        this.foregroundNotificationClickListener = foregroundNotificationClickListener;
    }

    public ForegroundNotification(int id, String title, String description, int iconRes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconRes = iconRes;
    }

    /**
     * 初始化
     * @return ForegroundNotification
     */
    public static ForegroundNotification ini(){
        return new ForegroundNotification();
    }
    /**
     * 设置标题
     * @param title 标题
     * @return ForegroundNotification
     */
    public ForegroundNotification title(@NonNull String title){
        this.title = title;
        return this;
    }
    /**
     * 设置副标题
     * @param description 副标题
     * @return ForegroundNotification
     */
    public ForegroundNotification description(@NonNull String description){
        this.description = description;
        return this;
    }
    /**
     * 设置图标
     * @param iconRes 图标
     * @return ForegroundNotification
     */
    public ForegroundNotification icon(@NonNull int iconRes){
        this.iconRes = iconRes;
        return this;
    }
    /**
     * 设置图标
     * @param iconRes 图标
     * @return ForegroundNotification
     */
    public ForegroundNotification id(@NonNull int id){
        this.id = id;
        return this;
    }
    /**
     * 设置前台通知点击事件
     * @param foregroundNotificationClickListener 前台通知点击回调
     * @return ForegroundNotification
     */
    public ForegroundNotification foregroundNotificationClickListener(@NonNull ForegroundNotificationClickListener foregroundNotificationClickListener){
        this.foregroundNotificationClickListener = foregroundNotificationClickListener;
        return this;
    }

    public String getTitle() {
        return title==null?"":title;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description==null?"":description;
    }

    public int getIconRes() {
        return iconRes;
    }

    public ForegroundNotificationClickListener getForegroundNotificationClickListener() {
        return foregroundNotificationClickListener;
    }
}
