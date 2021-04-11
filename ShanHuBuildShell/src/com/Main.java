package com;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    //读取配置文件路径和脚本输出路径
    //private static final String configAndShellFilePath = FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "\\ShanHuReport\\";
    private static final String configAndShellFilePath = getJarPath();

    //sdk项目位置
    private static String sdkPath;

    /**
     * 路径参数列表：list保存两个变量
     * 第一个路径是读取配置文件的路径（40805.dat）
     * 第二个路径是sdk项目的位置
     */
    private static final List<String> configList = new ArrayList<>();

    //路径配置
    private static final String configFileName = "configFile.txt";

    //读取的包名文件名称
    private static final String packNamesFileName = "packNameFile.txt";

    //包名list
    private static final List<String> packNameList = new ArrayList<>();

    //gradle文件名称
    private static final String gradleFileName = "build.gradle";

    //测试打包脚本文件名称
    private static final String testShellFileName = "testPackingShell.bat";

    //正式打包脚本文件名称
    private static final String releaseShellFileName = "releasePackingShell.bat";

    private static File configFlie;
    private static boolean isTestMode;
    private static String appKey;
    private static String nativeAdId;
    private static String rewardAdId;

    public static void main(String[] args) throws IOException {

        //创建相应的文件夹
        File file = new File(configAndShellFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //创建文件
        File packNames = new File(configAndShellFilePath + packNamesFileName);
        configFlie = new File(configAndShellFilePath + configFileName);

        if (!configFlie.exists()) {
            //提示信息写到配置文件当中
            String tips = "sdk项目路径(必须要有)：\n" +
                    "\n" +
                    "是否为测试模式(true/false)：true\n" +
                    "\n" +
                    "app key（默认值：0QY40DHLM2J08XXCDDS63A34）：\n" +
                    "\n" +
                    "原生广告id（默认值：17916_65526）：\n" +
                    "\n" +
                    "激励广告id（默认值：17916_29418）：";
            try {
                writeShellToFile(tips, configFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        loadFromFile(packNames, 0);
        loadFromFile(configFlie, 1);
        handleConfigInfo(configList);

        BuildShell buildShell = new BuildShell(packNameList, sdkPath, configAndShellFilePath);
        buildShell.isTestMode = isTestMode;
        buildShell.appkey = appKey;
        buildShell.nativeAdId = nativeAdId;
        buildShell.rewardAdId = rewardAdId;

        if (packNameList.size() > 0) {
            //将脚本写到文件中
            try {
                writeShellToFile(buildShell.buildGradleShell(), gradleFileName);
                writeShellToFile(buildShell.buildTestShell(), testShellFileName);
                writeShellToFile(buildShell.buildReleaseShell(), releaseShellFileName);
                String batFilePath = configAndShellFilePath + "MyDemo\\buildSusses.bat";
                Runtime.getRuntime().exec(batFilePath);
            } catch (Exception e) {
                String batFilePath = configAndShellFilePath + "MyDemo\\buildFail.bat";
                Runtime.getRuntime().exec(batFilePath);
                e.printStackTrace();
            }
        }
    }

    //获取jar的当前路径你
    public static String getJarPath() {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String s = path.replaceFirst("/", "");
        String[] split = s.split("/");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < (split.length - 1); i++) {
            System.out.println(split[i]);
            builder.append(split[i] + "\\");
        }
        return builder.toString();
    }

    /**
     * 从文件中获取配置信息
     *
     * @param file：要读取的文件
     * @param fileType：文件类型“0”代表包名文件，“1”代表配置文件
     */
    private static void loadFromFile(File file, int fileType) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            switch (fileType) {
                case 0:
                    while ((line = br.readLine()) != null) {
                        if (line.length() > 0) {
                            packNameList.add(line);
                        }
                    }
                    break;
                case 1:
                    while ((line = br.readLine()) != null) {
                        if (line.length() > 0) {
                            configList.add(line);
                        }
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将脚本写到文件当中
     *
     * @param shellStr：脚本
     * @param fileName：文件名称
     */
    private static void writeShellToFile(String shellStr, String fileName) throws Exception {
        File file = new File("");

        if (gradleFileName.equals(fileName)) {
            file = new File(sdkPath + "app\\" + fileName);
        }

        if (testShellFileName.equals(fileName) || releaseShellFileName.equals(fileName)) {
            file = new File(sdkPath + fileName);
        }

        if (configFileName.equals(fileName)) {
            file = configFlie;
        }

        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("文件创建成功~");
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        osw.write(shellStr);
        osw.flush();
        osw.close();
        fos.close();
    }

    //因为读取配置文件信息的时候有其他文字，我们要提取出我们需要的信息
    public static void handleConfigInfo(List<String> configList) {
        if (configList != null && configList.size() > 0) {
            for (int i = 0; i < configList.size(); i++) {
                String s = configList.get(i);
                System.out.println("读取到的信息 == >" + s);
                String substring = "";
                if (s.contains("sdk项目路径(必须要有)：")) {
                    sdkPath = s.replace("sdk项目路径(必须要有)：","") + "\\";
                    sdkPath = sdkPath.trim();
                    System.out.println("sdk路径 == > " + sdkPath);
                }
                if (s.contains("是否为测试模式(true/false)：")) {
                    substring = s.replace("是否为测试模式(true/false)：","");
                    if (substring.length() == 0) {
                        isTestMode = true;
                    } else {
                        isTestMode = Boolean.parseBoolean(substring);
                    }
                }
                if (s.contains("app key（默认值：0QY40DHLM2J08XXCDDS63A34）：")) {
                    substring = s.replace("app key（默认值：0QY40DHLM2J08XXCDDS63A34）：","");
                    if (substring.length() == 0) {
                        appKey = "0QY40DHLM2J08XXCDDS63A34";
                    } else {
                        appKey = substring;
                    }
                }
                if (s.contains("原生广告id（默认值：17916_65526）：")) {
                    substring = s.replace("原生广告id（默认值：17916_65526）：","");
                    if (substring.length() == 0) {
                        nativeAdId = "17916_65526";
                    } else {
                        nativeAdId = substring;
                    }
                }
                if (s.contains("激励广告id（默认值：17916_29418）：")) {
                    substring = s.replace("激励广告id（默认值：17916_29418）：","");
                    if (substring.length() == 0) {
                        rewardAdId = "17916_29418";
                    } else {
                        rewardAdId = substring;
                    }
                }
            }
        }
    }
}