package com.imooc.useraction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateUserActionDataLyx {

    public static void main(String[] args) {
        String inputFilePath = "E:\\Download\\BaiduNetdiskDownload\\大数据课程\\体系课大数据工程师2023版【完结】\\大数据课件\\课程源码+软件包下载地址\\db_data_warehouse\\data\\GenerateUserActionData.json"; // 替换为你的输入文件路径
        String outputFilePath = "E:\\Download\\BaiduNetdiskDownload\\大数据课程\\体系课大数据工程师2023版【完结】\\大数据课件\\课程源码+软件包下载地址\\db_data_warehouse\\data\\GenerateUserActionDataLyx.txt"; // 替换为你想要输出的文件路径

        try {
            // 读取并解析JSON文件
            String jsonString = new String(Files.readAllBytes(Paths.get(inputFilePath)), StandardCharsets.UTF_8);
            JSONObject jsonRoot = JSON.parseObject(jsonString);
            JSONArray jsonData = jsonRoot.getJSONArray("data");

            // 创建一个BufferedWriter用于写入输出文件
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath), StandardCharsets.UTF_8)) {
                // 遍历每个用户数据
                for (int i = 0; i < jsonData.size(); i++) {
                    JSONObject userObj = jsonData.getJSONObject(i);
                    String userDataStr = userObj.getString("data"); // 获取用户行为日志的JSON字符串

                    // 解析用户行为日志JSON字符串为数组
                    JSONArray userActArray = JSONArray.parseArray(userDataStr);

                    // 遍历每个用户行为日志
                    for (int j = 0; j < userActArray.size(); j++) {
                        JSONObject actObj = userActArray.getJSONObject(j);

                        // 创建一个新的JSON对象，包含用户信息和当前行为日志
                        JSONObject scatteredObj = new JSONObject();
                        // 复制除了data以外的所有用户信息
                        for (String key : userObj.keySet()) {
                            if (!"data".equals(key)) {
                                scatteredObj.put(key, userObj.get(key));
                            }
                        }
                        // 合并当前行为日志
                        scatteredObj.putAll(actObj);

                        // 将新的JSON对象写入到输出文件，每个对象后追加换行符
                        writer.write(scatteredObj.toJSONString());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
