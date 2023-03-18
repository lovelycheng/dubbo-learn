package tech.lovelycheng.learn.dubbolearn.spi;

import org.apache.dubbo.common.extension.Adaptive;

/**
 * @author chengtong
 * @date 2023/3/9 16:37
 */
@Adaptive
public class DubboSpiTestInterfaceImpl implements DubboSpiTestInterface {

    @Override
    public void ddd() {
        System.err.println("see see see");
    }
}
