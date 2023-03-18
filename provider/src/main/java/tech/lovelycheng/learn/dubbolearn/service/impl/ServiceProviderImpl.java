package tech.lovelycheng.learn.dubbolearn.service.impl;

import java.util.ArrayList;

import org.apache.dubbo.config.annotation.DubboService;

import tech.lovelycheng.learn.dubbolearn.service.ServiceProvider;

/**
 * @author chengtong
 * @date 2023/3/9 21:17
 */
@DubboService
public class ServiceProviderImpl implements ServiceProvider {
    @Override
    public void search(String path) {
        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 2});
    }

    public static void main(String[] args) {
        System.err.println(System.currentTimeMillis());
    }
}
