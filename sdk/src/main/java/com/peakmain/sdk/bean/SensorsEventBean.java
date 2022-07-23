package com.peakmain.sdk.bean;

/**
 * author ：Peakmain
 * createTime：2022/7/24
 * mail:2726449200@qq.com
 * describe：
 */

public class SensorsEventBean {

    private String event;
    private ParamsBean params;
    private Long time;
    private String event_date;

    public static class ParamsBean {
        private String sdk_lib;
        private Integer screen_width;
        private Integer screen_height;
        private String app_version;
        private String os;
        private String os_version;
        private String language;
        private String model;
        private String lib_version;
        private String brand;
        private String platform;
        private String element_path;
        private String page_path;
        private String page_title;
        private String title;
        private String element_type;
        private String element_id;
        private String element_content;

        public String getSdk_lib() {
            return sdk_lib;
        }

        public void setSdk_lib(String sdk_lib) {
            this.sdk_lib = sdk_lib;
        }

        public Integer getScreen_width() {
            return screen_width;
        }

        public void setScreen_width(Integer screen_width) {
            this.screen_width = screen_width;
        }

        public Integer getScreen_height() {
            return screen_height;
        }

        public void setScreen_height(Integer screen_height) {
            this.screen_height = screen_height;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getLib_version() {
            return lib_version;
        }

        public void setLib_version(String lib_version) {
            this.lib_version = lib_version;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getElement_path() {
            return element_path;
        }

        public void setElement_path(String element_path) {
            this.element_path = element_path;
        }

        public String getPage_path() {
            return page_path;
        }

        public void setPage_path(String page_path) {
            this.page_path = page_path;
        }

        public String getPage_title() {
            return page_title;
        }

        public void setPage_title(String page_title) {
            this.page_title = page_title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getElement_type() {
            return element_type;
        }

        public void setElement_type(String element_type) {
            this.element_type = element_type;
        }

        public String getElement_id() {
            return element_id;
        }

        public void setElement_id(String element_id) {
            this.element_id = element_id;
        }

        public String getElement_content() {
            return element_content;
        }

        public void setElement_content(String element_content) {
            this.element_content = element_content;
        }
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }
}
