package tech.lovelycheng.dubbolearn.loadbalance;

import java.util.Objects;

/**
 * @author chengtong
 * @date 2023/3/18 12:30
 */
public class Invoker {

    /**
     * 权重
     */
    int weight;
    /**
     * 当前权重
     */
    int currWeight;
    /**
     * 主键
     */
    String key;

    public Invoker(int weight, String key) {
        this.weight = weight;
        this.key = key;
        this.currWeight = 0;
    }

    @Override
    public String toString() {
        return "Invoker{" + "weight=" + weight + ", key='" + key + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Invoker))
            return false;
        Invoker invoker = (Invoker) o;
        return key.equals(invoker.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
