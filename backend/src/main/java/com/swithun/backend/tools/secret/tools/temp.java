/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-12 10:23:25
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-16 19:49:39
 */
package com.swithun.backend.tools.secret.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class temp {

    Map<String, Set<String>> piles = new HashMap<>();

    public Boolean check(String key, String back) {
        if (key == back) return false;

        Boolean flag = true;
        // 出度 (顺序出度， 时间出度)
        // 1 检查所有顺序出度 (所有直接相联的下一个结点)
        Set<String> set = piles.get(key);
        // 1.1 递归检查所有连通结点 -> 是否有 back
        for (String str : set) {
            // 只要 遇到 back -> 标志改为 false;
            flag &= check(str, back);
        }
        // 2 检查所有时间出度 (结束时间更晚一些的那个结点(与 key 结束时间相差最小的那个)) 

        // for (遍历 tPlans)
        //     找到(最近的结束时间更晚一些的那个结点) -> nextkey
        // flag &= check(nextkey, back)
        return flag;
    }

    public Boolean checkLoop(String key, String back) {
        // 拷贝 Piles
        Map<String, Set<String>> newPiles = new HashMap<>(piles);
        // 假设添加 relation
        newPiles.get(key).add(back);
        return checkLoopItem(newPiles, key, key, 0);

    }

    public Boolean checkLoopItem(Map<String, Set<String>> newPiles, String current, String begin, Integer time) {
        // 0 如果再次相遇->有环
        if (current == begin) {
            if (++time == 2) {
                return false;
            }
        }

        Boolean flag = true;

        Set<String> set = newPiles.get(current);

        // 1.1 尝试每一条 顺序 出度
        for (String next : set) {
            flag &= checkLoopItem(newPiles, next, begin, time);
        }
        // 1.2 尝试时间出度
        // String next = getNextTPlan();
        // flag &= checkLoopItem(newPiles, next, begin, time);

        return flag;
    }

    public Boolean isT(String key) {
        return key.startsWith("t");
    }
}
/*
 * @Descripttion:
 * 
 * @version:
 * 
 * @@Company: None
 * 
 * @Author: Swithun Liu
 * 
 * @Date: 2021-05-12 10:23:25
 * 
 * @LastEditors: Swithun Liu
 * 
 * @LastEditTime: 2021-05-12 10:24:40
 */
