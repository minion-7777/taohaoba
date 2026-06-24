package io.openim.android.taohaoba.utils;

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

public class HttpUtil {
//    public static void main(String[] args) {
//// 接口地址
//        String url = "https://api.chinadatapay.com/communication/personal/2061";
//// 请求参数
//        Map<String, Object> params = new HashMap<>();
//// 输入数据宝提供的key
//        params.put("key", "");
//// 姓名
//        params.put("name", "");
//// 身份证号
//        params.put("idcard", "");
//// 图片ID（建议图片不大于50KB，根据接口《数据宝图片上传接口文档.pdf》返回值“data”获取，例如：“a54ad2ce022b4da689d9081a5eaeb4f8”）
//        params.put("imageId", "");
//        String result = null;
//        try {
//            result = post(url, params);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("result:\n" + result);
//    }
//
//    public static String post(String url, Map<String, Object> params) throws Exception {
//        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
//        return PostHttpRequest(url, pairs);
//    }
//
//    public static String PostHttpRequest(String Url, List<NameValuePair> params) throws Exception {
//        CloseableHttpClient client = HttpClients.createDefault();
////超时时间
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setSocketTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        String result = null;
//        try {
//            HttpPost request = new HttpPost(Url);
//            equest.setConfig(requestConfig);
//            request.setEntity(new UrlEncodedFormEntity(params, "UTF‐8"));
//            HttpResponse respones = client.execute(request);
//            if (respones.getStatusLine().getStatusCode() == 200) {
//                result = EntityUtils.toString(respones.getEntity(), "UTF‐8");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            client.close();
//        }
//        return result;
//    }
//
//    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
//        ArrayList<NameValuePair> pairs = new ArrayList<>();
//        if (params == null || params.size() == 0) {
//            return pairs;
//        }
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            Object value = param.getValue();
//            if (value instanceof String[]) {
//                String[] values = (String[]) value;
//                for (String v : values) {
//                    pairs.add(new BasicNameValuePair(param.getKey(), v));
//                }
//            } else {
//                if (value instanceof Integer) {
//                    value = Integer.toString((Integer) value);
//                } else if (value instanceof Long) {
//                    value = Long.toString((Long) value);
//                }
//                pairs.add(new BasicNameValuePair(param.getKey(), (String) value));
//            }
//        }
//        return pairs;
//    }
}