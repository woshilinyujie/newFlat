package com.wl.wlflatproject.Presenter;

/**
 * 序列号连接设备,根据设备类型的序列号及账号密码,或者IP设备端口及账号密码,登录设备.
 * Created by jiangping on 2018-10-23.
 */
public class DevSnConnectContract {
    public interface IDevSnConnectView {
        void onAddDevResult(boolean isSuccess);
    }

    public interface IDevSnConnectPresenter {
        /**
         * 添加设备
         * @param devId
         */
        void addDev(String devId, String userName, String pwd, int devType);
    }
}
