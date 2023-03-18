package tech.lovelycheng.dubbolearn.loadbalance;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class ConsistentHashingCheck {

    // 定义哈希环的大小
    private static final int M = Integer.MAX_VALUE;

    // 定义虚拟节点的数量
    private static final int N = 1000;

    public static void main(String[] args) {
        // 生成虚拟节点的 ID
        String[] virtualNodeIds = new String[N];
        for (int i = 0; i < N; i++) {
            virtualNodeIds[i] = Integer.toString(i);
        }

        // 计算每个虚拟节点在哈希环上的位置
        int[] virtualNodePositions = new int[N];
        for (int i = 0; i < N; i++) {
            virtualNodePositions[i] = hashKey(virtualNodeIds[i]);
        }

        // 统计每个位置上虚拟节点的数量
        int[] positionCounts = new int[M];
        for (int i = 0; i < N; i++) {
            int pos = virtualNodePositions[i];
            positionCounts[pos]++;
        }

        // 计算最大和最小的虚拟节点数量
        int maxCount = Arrays.stream(positionCounts).max().getAsInt();
        int minCount = Arrays.stream(positionCounts).min().getAsInt();

        // 计算虚拟节点数量的标准差
        double avgCount = (double) Arrays.stream(positionCounts).sum() / M;
        double stdDev = Math.sqrt(Arrays.stream(positionCounts).mapToDouble(count -> (count - avgCount) * (count - avgCount)).sum() / M);

        // 输出结果
        System.out.println("虚拟节点数量的标准差为：" + stdDev);
        System.out.println("最大虚拟节点数量为：" + maxCount);
        System.out.println("最小虚拟节点数量为：" + minCount);
    }

    // 定义哈希函数
    private static int hashKey(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] digest = md.digest();
            return Math.abs(new Random().nextInt()) % M;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found");
        }
    }
}