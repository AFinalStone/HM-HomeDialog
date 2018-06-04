package com.hm.iou.homedialog.dict;

/**
 * Created by hjy on 2018/5/29.
 */

public enum DialogType {

    OfficeMsg("1", "官方私信"),
    MustUpdate("2", "重要升级"),
    Update("3", "发现新版本"),
    AdvertisementMoney("4", "活动广告(红包)"),
    AdvertisementOther("5", "活动广告(其他)");

    private String value;
    private String desc;

    DialogType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
