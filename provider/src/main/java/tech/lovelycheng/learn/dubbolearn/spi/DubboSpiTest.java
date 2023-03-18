package tech.lovelycheng.learn.dubbolearn.spi;

import java.util.List;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @author chengtong
 * @date 2023/3/9 16:36
 */
@Component
public class DubboSpiTest implements SmartLifecycle {

    volatile boolean isRunning;
    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        this.isRunning = false;
    }

    @Override
    public void start() {
        this.isRunning = true;

        ExtensionLoader<DubboSpiTestInterface> extensionLoader = ExtensionLoader.getExtensionLoader(DubboSpiTestInterface.class);
        DubboSpiTestInterface dubboSpiTestInterface = extensionLoader.getAdaptiveExtension();
        dubboSpiTestInterface.ddd();
        DubboSpiTestInterface d = extensionLoader.getExtension("demo");
        d.ddd();
        // DubboSpiTestInterface s  = extensionLoader.getActivateExtension();
        // s.ddd();
        DubboSpiTestInterface de  = extensionLoader.getDefaultExtension();
        de.ddd();
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
