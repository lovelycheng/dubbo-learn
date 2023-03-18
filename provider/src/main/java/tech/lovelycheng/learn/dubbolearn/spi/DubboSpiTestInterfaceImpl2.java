package tech.lovelycheng.learn.dubbolearn.spi;

import org.apache.dubbo.common.extension.Activate;


/**
 * @author chengtong
 * @date 2023/3/9 16:37
 */
public class DubboSpiTestInterfaceImpl2 implements DubboSpiTestInterface {

    @Override
    public void ddd() {
        System.err.println("d d d");
    }
}
