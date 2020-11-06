package com.wl.wlflatproject;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;

import com.lib.EFUN_ATTR;
import com.lib.FunSDK;
import com.lib.SDKCONST;
import com.lib.sdk.bean.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.manager.XMFunSDKManager;
import com.utils.FileUtils;
import com.utils.XUtils;
import com.wl.wlflatproject.Activity.MainActivity;
import com.wl.wlflatproject.MUtils.SafeHostnameVerifier;
import com.wl.wlflatproject.MUtils.SafeTrustManager;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import okhttp3.OkHttpClient;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject
 * @Author: HSL
 * @Time: 2019/07/18 15:55
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class AppContext extends Application {
    public static String DEFAULT_PATH;
    public static String PATH_DEVICE_TEMP;
    public static String PATH_PHOTO_TEMP;
    public static String PATH_PHOTO;
    public static String PATH_VIDEO;
    private static AppContext instance;
    private CrashHandler crashHandler;

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        XMFunSDKManager.getInstance().initXMCloudPlatform(this).initLog();
        initPath();
        initOKGO();
        crashHandler = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        FunSDK.SetFunIntAttr(EFUN_ATTR.SUP_RPS_VIDEO_DEFAULT, SDKCONST.Switch.Open);
    }

    /**
     * 初始化OkGo
     */
    private void initOKGO() {
        ////公共头部
        // TODO: 2018/10/12 header不支持中文，不允许有特殊字符
        HttpHeaders headers = new HttpHeaders();
        //标识应用名称，可取值：新版王力智能（wonlynew）、老版王力智能（wonlyold）
        headers.put("appId", "wonlynew");
        //标识手机系统平台，可取值：
        // ios（苹果系统）、android（安卓系统）、iosHD（苹果平板系统）、
        // androidHD（安卓平板系统）、wm7（微软手机系统）
        headers.put("phoneType", "android");
        ////公共参数
        // TODO: 2018/10/12 param支持中文,直接传,不要自己编码
        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");
//        params.put("commonParamsKey2", "这里支持中文参数");

        ////OkHttp相关配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //添加OkGo默认debug日志
        builder.addInterceptor(loggingInterceptor);

        ////超时时间设置，默认60秒
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        ////自动管理cookie（或者叫session的保持）
        //使用sp保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        //使用内存保持cookie，app退出后，cookie消失
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        ////https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        ////配置https的域名匹配规则
        builder.hostnameVerifier(new SafeHostnameVerifier());
        // 其他统一的配置
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(3)
                .addCommonHeaders(headers)
                .addCommonParams(params);
        FunSDK.LogInit(0,"",0,"",1);

    }

    class CrashHandler implements Thread.UncaughtExceptionHandler {

        private Application app = null;

        public CrashHandler(Application app) {
            this.app = app;
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            // 此处示例发生异常后，重新启动应用
            Intent intent = new Intent(app, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("caught",ex.toString());
            app.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());

        }
    }

    private void initPath() {
        DEFAULT_PATH = XUtils.getMediaPath(this) + File.separator + "xmfunsdkdemo" + File.separator;
        PATH_PHOTO_TEMP = DEFAULT_PATH + ".temp_images";

        // 下载设备缩略图此路径可用
        PATH_DEVICE_TEMP = XUtils.getAndroidPath(this) + File.separator + ".temp_capture";

        PATH_PHOTO = getPathForPhoto();
        PATH_VIDEO = getPathForVideo();

        File pFile = new File(DEFAULT_PATH);
        if (pFile != null && !pFile.exists()) {
            makeRootDirectory(pFile.getPath());
        }

        pFile = new File(PATH_PHOTO_TEMP);
        if (pFile != null && !pFile.exists()) {
            makeRootDirectory(pFile.getPath());
        }

        pFile = new File(PATH_DEVICE_TEMP);
        if (pFile != null && !pFile.exists()) {
            makeRootDirectory(pFile.getPath());
        }

        pFile = new File(PATH_PHOTO);
        if (pFile != null && !pFile.exists()) {
            makeRootDirectory(pFile.getPath());
        }

        pFile = new File(PATH_VIDEO);
        if (pFile != null && !pFile.exists()) {
            makeRootDirectory(pFile.getPath());
        }
    }
    public static boolean makeRootDirectory(String filePath) {
        File file = null;
        String newPath = null;
        String[] path = filePath.split("/");
        for (int i = 0; i < path.length; i++) {
            if (newPath == null) {
                newPath = path[i];
            } else {
                newPath = newPath + "/" + path[i];
            }
            if (StringUtils.isStringNULL(newPath)) {
                continue;
            }
            file = new File(newPath);
            if (!file.exists()) {
                return file.mkdir();
            }
        }
        return true;
    }
    private String getPathForPhoto() {
        SharedPreferences bell = getSharedPreferences("my_pref", 0);
        String path = bell.getString("img_save_path", null);
        if (path == null) {
            String galleryPath = Environment.getExternalStorageDirectory()
                    + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator + "Camera" + File.separator;
            if (!FileUtils.isFileAvailable(galleryPath)) {
                galleryPath = Environment.getExternalStorageDirectory()
                        + File.separator + Environment.DIRECTORY_DCIM
                        + File.separator;
            }
            if (!FileUtils.isFileAvailable(galleryPath)) {
                path = DEFAULT_PATH + "snapshot";
            } else {
                path = galleryPath;
            }
        }
        return path;
    }

    private String getPathForVideo() {
        SharedPreferences bell = getSharedPreferences("my_pref", 0);
        String path = bell.getString("video_save_path", null);
        if (path == null) {
            String galleryPath = Environment.getExternalStorageDirectory()
                    + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator + "Camera" + File.separator;
            if (!FileUtils.isFileAvailable(galleryPath)) {
                galleryPath = Environment.getExternalStorageDirectory()
                        + File.separator + Environment.DIRECTORY_DCIM
                        + File.separator;
            }
            if (!FileUtils.isFileAvailable(galleryPath)) {
                path = DEFAULT_PATH + "videorecord";
            } else {
                path = galleryPath;
            }
        }
        return path;
    }

}
