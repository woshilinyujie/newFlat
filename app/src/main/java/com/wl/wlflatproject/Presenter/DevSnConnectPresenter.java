package com.wl.wlflatproject.Presenter;

import android.os.Message;
import android.util.Log;

import com.basic.G;
import com.lib.MsgContent;
import com.lib.sdk.struct.SDBDeviceInfo;
import com.manager.account.AccountManager;
import com.manager.account.BaseAccountManager;
import com.manager.account.LocalAccountManager;
import com.manager.db.XMDevInfo;
import com.xm.activity.base.XMBasePresenter;


/**
 * 序列号连接设备,根据设备类型的序列号及账号密码,或者IP设备端口及账号密码,登录设备.
 * Created by jiangping on 2018-10-23.
 */
public class DevSnConnectPresenter extends XMBasePresenter<LocalAccountManager> implements DevSnConnectContract.IDevSnConnectPresenter {


    public DevSnConnectPresenter() {
    }

    @Override
    protected LocalAccountManager getManager() {
        return LocalAccountManager.getInstance();
    }


    @Override
    public void addDev(String devId,String userName,String pwd,int devType) {
        SDBDeviceInfo deviceInfo = new SDBDeviceInfo();
        G.SetValue(deviceInfo.st_0_Devmac,devId);
        G.SetValue(deviceInfo.st_5_loginPsw,pwd);
        G.SetValue(deviceInfo.st_4_loginName,userName);
        deviceInfo.st_7_nType = devType;
        XMDevInfo xmDevInfo = new XMDevInfo();
        xmDevInfo.sdbDevInfoToXMDevInfo(deviceInfo);
        manager.addDev(xmDevInfo, new BaseAccountManager.OnAccountManagerListener() {
            @Override
            public void onSuccess(int i) {
                Log.e("添加成功","");
            }

            @Override
            public void onFailed(int i, int i1) {

            }

            @Override
            public void onFunSDKResult(Message message, MsgContent msgContent) {

            }
        });
    }
}

