package com.wl.wlflatproject.Presenter;

import android.os.Message;

import com.lib.EUIMSG;
import com.lib.MsgContent;
import com.lib.sdk.struct.SDK_ChannelNameConfigAll;
import com.manager.account.AccountManager;
import com.manager.account.BaseAccountManager;
import com.manager.device.DeviceManager;
import com.xm.activity.base.XMBasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.manager.db.XMDevInfo.OFF_LINE;

/**
 * 分享的设备界面,显示相关的列表菜单
 * Created by jiangping on 2018-10-23.
 */
public class DevListConnectPresenter extends XMBasePresenter<AccountManager>
        implements  BaseAccountManager.OnDevStateListener {

    private List<HashMap<String,Object>> devList;
    public DevListConnectPresenter() {
    }

    @Override
    public AccountManager getManager() {
        return AccountManager.getInstance();
    }



    @Override
    public void onUpdateDevState(String s) {

    }

    @Override
    public void onUpdateCompleted() {

    }
}

