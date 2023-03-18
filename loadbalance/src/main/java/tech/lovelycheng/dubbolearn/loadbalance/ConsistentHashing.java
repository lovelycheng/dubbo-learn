package tech.lovelycheng.dubbolearn.loadbalance;

import java.util.*;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.io.Bytes;

import com.google.common.collect.HashMultiset;

public class ConsistentHashing {
    private final TreeMap<Long, Invoker> circle = new TreeMap<Long, Invoker>();
    private final int numberOfReplicas;

    //numberOfReplicas = 160 每个server的
    public ConsistentHashing(int numberOfReplicas, List<Invoker> nodes, int hashFunction) {
        this.numberOfReplicas = numberOfReplicas;
        List<Long> list = new ArrayList<>();
        for (Invoker node : nodes) {
            if (hashFunction == 0) {
                addNode(node,list);
            } else {
                addNodeMd5(node,list);
            }
        }
        logStatistic(list);
    }

    public void addNode(Invoker node,List list) {
        for (int i = 0; i < numberOfReplicas; i++) {
            Long hash = getHash(node.key + i);
            list.add(hash);
            circle.put(hash, node);
        }
    }

    public void addNodeMd5(Invoker node,List list) {
        for (int i = 0; i < numberOfReplicas/4; i++) {
            byte[] digest = Bytes.getMD5(String.valueOf(getHash(node) + i));
            for (int h = 0; h < 4; h++) {//每次加4
                long m = hash(digest, h);
                list.add(m);
                circle.put(m, node);
            }
        }
    }

    private long hash(byte[] digest, int number) {
        long h = (((long) (digest[3 + number * 4] & 0xFF) << 24) | ((long) (digest[2 + number * 4] & 0xFF) << 16) | (
            (long) (digest[1 + number * 4] & 0xFF) << 8) | (digest[number * 4] & 0xFF)) & 0xFFFFFFFFL;
        return h;
    }

    public void removeNode(String node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            Long hash = getHash(node + i);
            circle.remove(hash);
        }
    }

    public Invoker getNode(Object key,List<Long> list) {
        if (circle.isEmpty()) {
            return null;
        }

        Long hash = getHash(key);
        list.add(hash);
        if (!circle.containsKey(hash)) {
            SortedMap<Long, Invoker> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }

        return circle.get(hash);
    }

    public Invoker getNodeMd5(Object key,List<Long> list) {
        if (circle.isEmpty()) {
            return null;
        }

        Long s =  hash(Bytes.getMD5(String.valueOf(getHash(key))), 0);
        list.add(s);
        Long ceilingKey = circle.ceilingKey(s);
        if(ceilingKey == null){
            return circle.firstEntry()
                .getValue();
        }
        return circle.get(ceilingKey);

    }

    private long getHash(Object key) {
        // Use a hash function to get a 32-bit integer hash value
        // Here, we use the Java built-in hash function
        int hashcode = key.hashCode();
        return (hashcode >>> 16) ^ hashcode;
    }

    public static void main(String[] args) {
        List<Invoker> strings = new ArrayList<>();
        String server = "server";
        for (int i = 0; i < 3; i++) {
            strings.add(new Invoker(1, server + i));
        }

        hashTest0(strings, 0);
        hashTest0(strings, 1);
    }

    private static void hashTest0(List<Invoker> strings, int hashf) {
        ConsistentHashing consistentHashing = new ConsistentHashing(300, strings, hashf);

        HashMultiset<Invoker> hashMultiset = HashMultiset.create();
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            if (hashf == 0) {
                hashMultiset.add(consistentHashing.getNode(new Object(),list));
            } else {
                hashMultiset.add(consistentHashing.getNodeMd5(new Object(),list));
            }
        }
        System.err.println(hashMultiset.count(new Invoker(1, "server0")));
        System.err.println(hashMultiset.count(new Invoker(1, "server1")));
        System.err.println(hashMultiset.count(new Invoker(1, "server2")));

        logStatistic(list);

    }

    private static void logStatistic(List<Long> list) {
        double avgCount =  (double) list.stream().mapToLong(Long::longValue).sum()/ list.size();
        double stdDev = Math.sqrt(list.stream().mapToDouble(count -> (count - avgCount) * (count - avgCount)).sum() / list.size());
        long min = list.stream().mapToLong(Long::longValue).min().getAsLong();
        long max = list.stream().mapToLong(Long::longValue).max().getAsLong();
        System.out.println("分布的标准差为：" + stdDev);
        System.out.println("数量为：" + list.size());
        System.out.println("min：" + min);
        System.out.println("max：" + max);
        System.out.println("avgCount：" + avgCount);
        System.out.println("");
        System.out.println("");
    }

}