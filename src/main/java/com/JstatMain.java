package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JstatMain {
    /**
     * S0 — Heap上的 Survivor space 0 区已使用空间的百分比
     * S1 — Heap上的 Survivor space 1 区已使用空间的百分比
     * E — Heap上的 Eden space 区已使用空间的百分比
     * O — Heap上的 Old space 区已使用空间的百分比
     * M — Metaspace区已使用空间的百分比
     * CCS－j class space capacity (kB)
     * YGC — 从应用程序启动到采样时发生 Young GC 的次数
     * YGCT– 从应用程序启动到采样时 Young GC 所用的时间(单位秒)
     * FGC — 从应用程序启动到采样时发生 Full GC 的次数
     * FGCT－从应用程序启动到采样时 Full GC 所用的时间(单位秒)
     * GCT — 从应用程序启动到采样时用于垃圾回收的总时间(单位秒)
     */
    public static void main(String args[]) throws IOException {

        Map<String, Object> monitor = new HashMap<>();

        List<String> statColumn = Arrays.asList("S0", "S1", "E", "O", "M", "CCS", "YGC", "YGCT", "FGC", "FGCT", "GCT");

        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
        String pid = name.substring(0, name.indexOf("@"));
        Process process = Runtime.getRuntime().exec("jstat -gcutil " + pid + " 5000");

        //通过process拿到jstat命令的执行结果的输入流
        InputStreamReader isr = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(isr);

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] stats = line.trim().split("[ ]+");
            if (stats.length == statColumn.size()) {
                for (int i = 0; i < stats.length; i++) {
                    monitor.put(statColumn.get(i), stats[i]);
                    System.out.println(statColumn.get(i) + ":" + stats[i]);
                }
                System.out.println("=====");
            }
        }
    }
}