package tech.lovelycheng.learn.dubbolearn.spi;

import org.apache.dubbo.common.extension.Activate;

/**
 * @author chengtong
 * @date 2023/3/9 16:37
 */
@Activate
public class DubboSpiTestInterfaceImpl3 implements DubboSpiTestInterface {

    @Override
    public void ddd() {
        System.err.println("sshshssh");
    }
}
