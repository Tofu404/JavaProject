package com.hello;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        outputTest();

        formatOutPut();

        forTest();

        reversalArray();

        calculateAverage();
    }

    /**
     * 计算平均值
     */
    private static void calculateAverage() {
        //使用二维数组可以表示一组学生的各科成绩，请计算所有学生的平均分：
        // 用二维数组表示的学生成绩:
        int[][] scores = {
                {82, 90, 91},
                {68, 72, 64},
                {95, 91, 89},
                {67, 52, 60},
                {79, 81, 85},
        };
        double average;
        double count = 0;
        int sum = 0;
        for (int[] score : scores) {
            for (int i : score) {
                sum += i;
                count++;
            }
        }
        average = sum / count;
        System.out.println(average);

        if (Math.abs(average - 77.733333) < 0.000001) {
            System.out.println("测试成功");
        } else {
            System.out.println("测试失败");
        }
    }

    /**
     * 倒叙排序
     */
    private static void reversalArray() {
        int[] ns = {28, 12, 89, 73, 65, 18, 96, 50, 8, 36};
        System.out.println(Arrays.toString(ns));
        for (int i = 0; i < ns.length; i++) {
            for (int j = 0; j < ns.length - 1 - i; j++) {
                if (ns[j] < ns[j + 1]) {
                    int tem = ns[j];
                    ns[j] = ns[j + 1];
                    ns[j + 1] = tem;
                }
            }
        }
        System.out.println(Arrays.toString(ns));
        if (Arrays.toString(ns).equals("[96, 89, 73, 65, 50, 36, 28, 18, 12, 8]")) {
            System.out.println("测试成功");
        } else {
            System.out.println("测试失败");
        }
    }

    /**
     * for循环测试：计算pi
     */
    private static void forTest() {
        new Thread(() -> {
            double pi = 0;
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                pi = i % 2 == 0 ? pi - (1.0 / (i * 2 - 1)) : pi + (1.0 / (i * 2 - 1));
            }
            System.out.println("pi中的值 == > " + pi);
        }).start();
    }

    /**
     * 格式化输出
     */
    private static void formatOutPut() {
        byte b = (byte) 255;
        String format = String.format("%2x", b);
        System.out.println("格式化输出 == > " + format);
    }

    /**
     * 输出测试
     */
    private static void outputTest() {
        System.out.println("Hello World! ");
    }
}
