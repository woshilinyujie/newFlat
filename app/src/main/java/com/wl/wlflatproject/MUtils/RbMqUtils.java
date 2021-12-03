package com.wl.wlflatproject.MUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2019/1/29.
 */

public class RbMqUtils {

    // 创建内部消息队列，供消费者发布消息用
    private BlockingDeque<String> queue = new LinkedBlockingDeque<>();
    private ConnectionFactory factory = new ConnectionFactory();
    private Thread publishThread;
    private Thread subscribeThread;
    private Connection connectionAccept;
    private OnRbMsgListener listener;
    private Connection connection;
    private String isConnection ="+CGATT:0";//0断开1链接
    private String mie;
    public boolean flag=true;
    public static String MQIP="rmq.wonlycloud.com";
    public boolean connect=true;
    public void RbMqUtils() {

    }

    /**
     * 连接设置
     */
    public void setUpConnectionFactory() {
        factory.setHost(MQIP);//主机地址
//        factory.setHost("116.62.235.84");//灰度
//        factory.setHost("116.62.46.10");//测试

        factory.setPort(5672);// 端口号
        factory.setUsername("android");// 用户名
        factory.setPassword("Wl2016822");// 密码
        factory.setAutomaticRecoveryEnabled(true);// 设置连接恢复
    }

    public void publishToAMPQ(final String routingKey) {
        publishThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection(routingKey);
            }
        });
        publishThread.start();
    }


    private void  Connection(String routingKey){
        while (connect){
            try {
                // 创建连接
                Log.e("mq----","开始创建mq发送端");
                connectionAccept = factory.newConnection();
                // 创建通道
                Channel ch = connectionAccept.createChannel();
                ch.confirmSelect();
                // 发布消息
                ch.exchangeDeclare("Device2Srv_ex", "fanout", true);
                ch.queueBind("Device2Srv_queue", "Device2Srv_ex", "");
                Log.e("mq---","创建mq发送端成功");
                while (flag) {
                    String message = queue.takeFirst();
                    Log.e("队列拿到发送服务器","------"+message);
                    try {
                        ch.basicPublish("Device2Srv_ex", routingKey, null, message.getBytes());
                        ch.waitForConfirmsOrDie();
                    } catch(Exception e){
                        queue.putFirst(message);

                        throw e;
                    }
                }
            } catch (Exception e) {
                Log.e("mq---","发送端报错----"+e.toString());
                try {
                    publishThread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    public void pushMsg(String message) {
        //向内部阻塞队列添加一条消息
        try {
            if(queue.size()>20){
                queue.clear();
            }else{
                queue.putLast(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // 处理handler发送的消息，然后进行操作（在主线程）
    private Handler incomingMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 获取RabbitMQ的消息数据
            String messageData = msg.getData().getString("msg");
            listener.AcceptMsg(messageData);
        }

    };

    /**
     * 创建消费者线程，获取数据
     * @param //handler
     */
   public void subscribe(String mie){
       this.mie=mie;
       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               subscribeONe(mie);
           }
       });
       thread.start();
    }


    public void subscribeONe(String MIE){

        while (connect){
            try {
                //使用之前的设置，建立连接
                Log.e("mq----","开始创建mq接收端");
                connection = factory.newConnection();
                //创建一个通道
                Channel channel = connection.createChannel();
                //一次只发送一个，处理完成一个再获取z下一个
                channel.basicQos(1);

                // 声明交换机类型
                channel.exchangeDeclare("Srv2Device_ex", "direct", true);
                // 声明队列（持久的、非独占的、连接断开后队列会自动删除）
                Map<String, Object> mapAndroid = new HashMap<String, Object>();
                mapAndroid.put("x-expires",10000);
                AMQP.Queue.DeclareOk q = channel.queueDeclare("", true, false, true,mapAndroid);// 声明共享队列
                // 根据路由键将队列绑定到交换机上（需要知道交换机名称和路由键名称）
                channel.queueBind(q.getQueue(), "Srv2Device_ex", MIE);

                //创建消费者
                QueueingConsumer consumer = new QueueingConsumer(channel);
                channel.basicConsume(q.getQueue(), true, consumer);
                Log.e("mq----","创建Mq接收端成功");
                while (flag) {
                    isConnection ="+CGATT:1";
                    //wait for the next message delivery and return it.
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    String message = new String(delivery.getBody());


                    //从message池中获取msg对象更高效
                    Message msg = incomingMessageHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", message);
                    msg.setData(bundle);
                    Log.e("服务器-----ThreadName", Thread.currentThread().getName()+"----"+q.getQueue().toString());
                    incomingMessageHandler.sendMessage(msg);
                }
            }  catch (Exception e1) {
                Log.e("mq----","接收端报错"+ e1.toString());
                if(e1.toString().contains("com.rabbitmq.client")||e1.toString().contains("java.util.concurrent.TimeoutException")||e1.toString().contains("java.net")) {
                    isConnection ="+CGATT:0";
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void mClose(boolean connect){
       this.connect=connect;
       new Thread(new Runnable() {
           @Override
           public void run() {
               if(subscribeThread!=null||connection!=null||connectionAccept!=null){
                   try {
                       if(subscribeThread!=null){
                           subscribeThread.interrupt();
                       }
                       if(connection!=null && connection.isOpen()){
                           connection.close();
                       }
                       if(connectionAccept!=null&&connectionAccept.isOpen()){
                           connectionAccept.close();
                       }
                   }catch (Exception e){
                       Log.e("关闭报错",e.toString());
                   }
               }
           }
       }).start();
    }

    public void clearQueue(){
       queue.clear();
    }

    public void setRbMsgListener(OnRbMsgListener listener){
        this.listener=listener;
    }
    public interface OnRbMsgListener{
        void AcceptMsg(String msg);
    }


    public String isConnection(){
       return isConnection;
    }

}



