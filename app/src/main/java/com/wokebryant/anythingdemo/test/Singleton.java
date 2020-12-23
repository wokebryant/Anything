package com.wokebryant.anythingdemo.test;

/**
 *  单例模式
 */
public class Singleton {

    // volatile关键字防止指令重排
    public volatile static Singleton instance;

    private Singleton() {

    }

    // 双重锁定
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
