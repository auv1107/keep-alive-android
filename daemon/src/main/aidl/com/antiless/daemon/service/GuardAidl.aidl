package com.antiless.daemon.service;

interface GuardAidl {
    //相互唤醒服务
    void wakeUp(int id, String title, String discription, int iconRes);
}
