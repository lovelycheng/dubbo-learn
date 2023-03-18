package tech.lovelycheng.dubbolearn.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * @author chengtong
 * @date 2023/3/18 12:29
 */
public class WeightRoundRobin {

    private HashMap<Invoker,Integer> invokerWeightMap = new HashMap<>();

    public Invoker doSelect(List<Invoker> invokerList){
        if(invokerList.isEmpty()){
            return null;
        }
        int totalWeight = 0;
        Invoker best = null;
        for (Invoker invoker:invokerList){
            if(!invokerWeightMap.containsKey(invoker)){
                invokerWeightMap.put(invoker,invoker.weight);
            }else {
                if(!invokerWeightMap.get(invoker).equals(invoker.weight)){
                    invokerWeightMap.put(invoker,invoker.weight);
                }
            }
            totalWeight += invoker.weight;
            invoker.currWeight += invoker.weight;
            if(best == null || invoker.currWeight > best.currWeight){
                best = invoker;
            }
        }
        if(best == null){
            return invokerList.get(0);
        }
        best.currWeight -= totalWeight;
        return best;
    }

    public static void main(String[] args) {
        Invoker invoker1 = new Invoker(1,"s1");
        Invoker invoker2 = new Invoker(1,"s2");
        Invoker invoker3 = new Invoker(3,"s3");

        List<Invoker> list = new ArrayList<>();
        list.add(invoker1);
        list.add(invoker2);
        list.add(invoker3);

        WeightRoundRobin wrr = new WeightRoundRobin();

        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
        System.out.println(wrr.doSelect(list));
    }
}
