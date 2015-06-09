package com.yun9.jupiter.manager.support;

import android.content.Context;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.manager.DeviceManager;
import com.yun9.jupiter.model.Device;
import com.yun9.jupiter.util.PublicHelp;

import java.util.Map;

/**
 * Created by Leon on 15/6/9.
 */
public class DefaultDeviceManager implements DeviceManager,Bean,Initialization {

    private Device device;

    @Override
    public Class<?> getType() {
        return DeviceManager.class;
    }

    @Override
    public void init(BeanManager beanManager) {
        this.loadDeviceInfos(beanManager.getApplicationContext());
    }

    private void loadDeviceInfos(Context cxt) {
        // 收集设备信息
        Map<String, String> deviceInfo = PublicHelp.collectDeviceInfo(cxt);

        this.device = new Device();

        device.setId(deviceInfo.get("ID"));
        device.setDeviceid(deviceInfo.get("TMDeviceid"));
        device.setModel(deviceInfo.get("MODEL"));
        device.setBoard(deviceInfo.get("BOARD"));
        device.setBrand(deviceInfo.get("BRAND"));
        device.setFingerprint(deviceInfo.get("FINGERPRINT"));
        device.setSerial(deviceInfo.get("SERIAL"));
        device.setOthers(deviceInfo);
    }

    public Device getDevice() {
        return device;
    }

}
