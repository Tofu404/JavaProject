
package com;

import java.util.List;

public class BuildShell {

    private final String sdkPath;
    private final String configAndShellFilePath;
    public boolean isTestMode;
    private List<String> packNames;
    private StringBuilder builder = null;
    public String appkey;
    public String nativeAdId;
    public String rewardAdId;


    //构造方法
    public BuildShell(List<String> packNames, String sdkPath,String configAndShellFilePath) {
        this.packNames = packNames;
        this.sdkPath = sdkPath;
        this.configAndShellFilePath = configAndShellFilePath;

    }

    //构建graldle脚本
    public String buildGradleShell() {

        if (builder != null) {
            builder.delete(0, builder.length());
        } else {
            builder = new StringBuilder();
        }

        builder.append(
                "apply plugin: 'com.android.application'\n" +
                "\n" +
                "android {\n" +
                "    compileSdkVersion 29\n" +
                "    buildToolsVersion \"29.0.3\"\n" +
                "\n" +
                "    defaultConfig {\n" +
                "        applicationId \"com.alltheway.forward\"\n" +
                "        minSdkVersion 21\n" +
                "        targetSdkVersion 29\n" +
                "        versionCode 1\n" +
                "        versionName \"1.0\"\n" +
                "\n" +
                "        testInstrumentationRunner \"android.support.test.runner.AndroidJUnitRunner\"\n" +
                "    }\n" +
                "\n" +
                "    //多渠道打包\n" +
                "\n" +
                "    flavorDimensions \"default\"\n" +
                "\n" +
                "    productFlavors {\n\n"
        );

        for (int i = 0; i < packNames.size(); i++) {
            builder.append(
                    "        myApp"+(i+1)+" {\n" +
                    "            applicationId \""+packNames.get(i)+"\"\n" +
                    "            buildConfigField 'boolean', 'TEST_MODE', '"+isTestMode+"'\n" +
                    "            buildConfigField 'String', 'APPKEY', '\""+appkey+"\"'\n" +
                    "            buildConfigField 'String', 'NATIVEADID', '\""+nativeAdId+"\"'\n" +
                    "            buildConfigField 'String', 'REWARDADID', '\""+rewardAdId+"\"'\n" +
                    "            manifestPlaceholders = [app_name: \"第"+(i+1)+"个app\"]\n" +
                    "        }\n\n"
            );
        }


        builder.append(
                "    }\n" +
                "\n" +
                "    buildTypes {\n" +
                "        release {\n" +
                "            minifyEnabled false\n" +
                "            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}\n" +
                "\n" +
                "repositories {\n" +
                "    flatDir {\n" +
                "        dirs 'libs'\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "dependencies {\n" +
                "    implementation fileTree(dir: \"libs\", include: [\"*.jar\"])\n" +
                "    //noinspection GradleCompatible\n" +
                "    implementation 'com.android.support:appcompat-v7:28.0.0'\n" +
                "    implementation 'com.android.support.constraint:constraint-layout:1.1.3'\n" +
                "    testImplementation 'junit:junit:4.12'\n" +
                "    androidTestImplementation 'com.android.support.test:runner:1.0.2'\n" +
                "    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'\n" +
                "\n" +
                "    implementation 'com.github.bumptech.glide:glide:4.9.0'\n" +
                "    implementation 'com.google.code.gson:gson:2.8.5'\n" +
                "    implementation(name: 'kllib', ext: 'aar')\n" +
                "    implementation(name: 'kllib-ext-1', ext: 'aar')\n" +
                "    implementation(name: 'kllib-ext-2', ext: 'aar')\n" +
                "}"
        );

        return builder.toString();
    }

    //构建测试上报监本
    public String buildTestShell() {

        if (builder != null) {
            builder.delete(0, builder.length());
        } else {
            builder = new StringBuilder();
        }

        //将配置文件复制到目标文件夹
        builder.append(
                "@echo off\n" +
                "\n" +
                "if exist "+sdkPath+"app\\src\\main\\assets\\40805.dat (\n" +
                "\tdel "+sdkPath+"app\\src\\main\\assets\\40805.dat\n" +
                ")\n" +
                "copy /y "+configAndShellFilePath+"config\\40805.dat "+sdkPath+"app\\src\\main\\assets\\40805.dat\n" +
                "call gradlew app:assembleDebug\n"
        );

        for (int i = 0; i < packNames.size(); i++) {
            //将gradle文件复制到目标文件夹，并运行安装app
            builder.append(
                    "adb install -r "+sdkPath+"app\\build\\outputs\\apk\\myApp"+(i+1)+"\\debug\\app-myApp"+(i+1)+"-debug.apk\n" +
                    "adb shell am start -n "+packNames.get(i)+"/create.by.tianze.MainActivity\n" +
                    ":loop"+(i+1)+"\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo ################################\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo input \"1\" to reinstall the app\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo input \"2\" to go on\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo ################################\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "set /p num=\n" +
                    "if %num% == 1 (\n" +
                    "\tadb uninstall "+packNames.get(i)+"\n" +
                    "\tadb install -r "+sdkPath+"app\\build\\outputs\\apk\\myApp"+(i+1)+"\\debug\\app-myApp"+(i+1)+"-debug.apk\n" +
                    "\tadb shell am start -n "+packNames.get(i)+"/create.by.tianze.MainActivity\n" +
                    "\tset /a num=0\n" +
                    "\tgoto loop"+(i+1)+"\n" +
                    ")\n" +
                    "adb uninstall "+packNames.get(i)+"\n\n"
            );
        }
        return builder.toString();
    }

    //构建正式上报脚本
    public String buildReleaseShell() {

        if (builder != null) {
            builder.delete(0, builder.length());
        } else {
            builder = new StringBuilder();
        }

        builder.append(
                "@echo off\n"
        );

        for (int i = 0; i < packNames.size(); i++) {
            builder.append(
                    "copy /y "+configAndShellFilePath+"config\\"+(i+1)+".dat "+sdkPath+"app\\src\\main\\assets\\40805.dat\n" +
                    "call gradlew app:assembleMyApp"+(i+1)+"\n" +
                    "adb install -r "+sdkPath+"app\\build\\outputs\\apk\\myApp"+(i+1)+"\\debug\\app-myApp"+(i+1)+"-debug.apk\n" +
                    "adb shell am start -n "+packNames.get(i)+"/create.by.tianze.MainActivity\n" +
                    ":loop"+(i+1)+"\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo ################################\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo input \"1\" to reinstall the app\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo input \"2\" to go on\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "echo ################################\n" +
                    "echo.\n" +
                    "echo.\n" +
                    "set /p num=\n" +
                    "if %num% == 1 (\n" +
                    "\tadb uninstall "+packNames.get(i)+"\n" +
                    "\tadb install -r "+sdkPath+"app\\build\\outputs\\apk\\myApp"+(i+1)+"\\debug\\app-myApp"+(i+1)+"-debug.apk\n" +
                    "\tadb shell am start -n "+packNames.get(i)+"/create.by.tianze.MainActivity\n" +
                    "\tset /a num=0\n" +
                    "\tgoto loop"+(i+1)+"\n" +
                    ")\n" +
                    "adb uninstall "+packNames.get(i)+"\n\n"
            );
        }
        return builder.toString();
    }

}

