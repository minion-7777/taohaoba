package io.openim.android.taohaoba.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 解析json格式文件
 */
public class JsonUtils {

    // 读取 assets 文件夹中的 JSON 文件并返回 JSONArray
    public static JSONArray loadJsonArrayFromAssets(Context context, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 获取 AssetManager
            InputStream inputStream = context.getAssets().open(filename);

            // 使用 BufferedReader 读取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            inputStream.close(); // 关闭流

            // 将读取的字符串转为 JSONArray
            return new JSONArray(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
