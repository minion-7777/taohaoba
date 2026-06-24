//package io.openim.android.taohaoba.services;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.huawei.hms.aaid.HmsInstanceId;
//import com.huawei.hms.common.ApiException;
//import com.igexin.sdk.GTIntentService;
//import com.igexin.sdk.message.GTCmdMessage;
//import com.igexin.sdk.message.GTNotificationMessage;
//import com.igexin.sdk.message.GTTransmitMessage;
//import com.vivo.push.PushClient;
//import com.vivo.push.listener.IPushQueryActionListener;
//
//import io.openim.android.taohaoba.DemoApplication;
//
///**
// * 继承 GTIntentService 接收来自个推的消息，所有消息在主线程中回调，如果注册了该服务，则务必要在 AndroidManifest 中声明，否则无法接受消息
// */
//public class IntentService extends GTIntentService {
//
//    @Override
//    public void onReceiveServicePid(Context context, int pid) {
//    }
//
//    /**
//     * 此方法用于接收和处理透传消息。透传消息个推只传递数据，不做任何处理，客户端接收到透传消息后需要自己去做后续动作处理，如通知栏展示、弹框等。
//     * 如果开发者在客户端将透传消息创建了通知栏展示，建议将展示和点击回执上报给个推。
//     */
//    @Override
//    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
//        byte[] payload = msg.getPayload();
//        String data = new String(payload);
//        Log.d(TAG, "receiver payload = " + data);//透传消息文本内容
//
//        //taskid和messageid字段，是用于回执上报的必要参数。详情见下方文档“6.2 上报透传消息的展示和点击数据”
////        String taskid = msg.getTaskId();
////        String messageid = msg.getMessageId();
//
//    }
//
//    // 接收 cid
//    @Override
//    public void onReceiveClientId(Context context, String clientid) {
//        DemoApplication.clientid = clientid;
//        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
////        PushClient.getInstance(context).getRegId(new IPushQueryActionListener() {
////            @Override
////            public void onSuccess(String s) {
////                Log.e(TAG, "vivo -> " + "regid = " + s);
////            }
////
////            @Override
////            public void onFail(Integer integer) {
////
////            }
////        });
////        new Thread() {
////            @Override
////            public void run() {
////                try {
////                    // 从agconnect-services.json文件中读取APP_ID
////                    String token = HmsInstanceId.getInstance(context).getToken("114272475", "HCM");
////                    Log.e(TAG, "huawei -> " + "token = " + token);
////                } catch (ApiException e) {
////                    Log.e(TAG, "get token failed, " + e);
////                }
////            }
////        }.start();
//    }
//
//    // cid 离线上线通知
//    @Override
//    public void onReceiveOnlineState(Context context, boolean online) {
//    }
//
//    // 各种事件处理回执
//    @Override
//    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
//    }
//
//    // 通知到达，只有个推通道下发的通知会回调此方法
//    @Override
//    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
//        Log.e(TAG, "onReceiveTaskId -> " + "taskid = " + msg.getTaskId());
//
//    }
//
//    // 通知点击，只有个推通道下发的通知会回调此方法
//    @Override
//    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
//    }
//}
