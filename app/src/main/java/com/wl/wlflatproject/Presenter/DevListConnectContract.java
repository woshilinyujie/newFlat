package com.wl.wlflatproject.Presenter;

import com.manager.account.BaseAccountManager;

import java.util.HashMap;
import java.util.List;

/**
 * 分享的设备界面,显示相关的列表菜单
 * Created by jiangping on 2018-10-23.
 */
public class DevListConnectContract {
    public interface IDevListConnectView {
        void onUpdateDevStateResult(boolean isSuccess);
        void onModifyDevNameFromServerResult(boolean isSuccess);
        void onDeleteDevResult(boolean isSuccess);
        void onGetChannelListResult(boolean isSuccess, int resultId);
    }

    public interface IDevListConnectPresenter extends BaseAccountManager.OnAccountManagerListener {
        List<HashMap<String,Object>> getDevList();
        String getDevId(int position);
        void updateDevState();
        boolean isOnline(int position);
        void modifyDevNameFromServer(int position, String devName);
        void deleteDev(int position);
        void getChannelList();
    }
}
