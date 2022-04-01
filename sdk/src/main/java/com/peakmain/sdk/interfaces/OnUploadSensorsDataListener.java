package com.peakmain.sdk.interfaces;

import com.peakmain.sdk.constants.SensorsDataConstants;

/**
 * author ：Peakmain
 * createTime：2022/3/29
 * mail:2726449200@qq.com
 * describe：上传埋点信息接口
 */
public interface OnUploadSensorsDataListener {
    /**
     * 上传埋点
     *
     * @param state APP_START_EVENT_STATE = 1;
     *              APP_END__EVENT_STATE = 2;
     *              APP_VIEW_SCREEN__EVENT_STATE = 3;
     *              APP_VIEW_CLICK__EVENT_STATE = 4;
     * @param data 埋点数据
     */
    void onUploadSensors(@SensorsDataConstants.STATE int state, String data);
}
