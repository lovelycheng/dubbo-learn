package tech.lovelycheng.learn.dubbolearn.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author chengtong
 * @date 2023/3/9 16:37
 */
@SPI(value = "default")
public interface DubboSpiTestInterface {

    void ddd();

}
