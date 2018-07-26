#### HM-HomeDialog

集成了首页更新弹窗，广告弹窗，系统公告弹窗等功能

#### 功能介绍

- 主要实现了首页的软件更新弹窗提示，强制更新弹窗提示，首页广告弹窗，首页系统公告弹窗

#### 安装

在工程根目录的build.gradle文件里添加如下maven地址：

```gradle
allprojects {
    repositories {
        ...
        maven { url 'file:///Users/syl/.m2/repository/' }
        ...
    }
}
```

在项目模块的buile.gradle文件里面加入如下依赖：

```gradle
    compile 'com.heima.iou:hmhomedialoglocal:1.0.0'
```

外部引用：

```gradle
    compile 'com.heima.iou:hmbasebizlocal:1.0.0'
    compile 'com.heima.iou:hmdbcenterlocal:1.0.0'
```

#### 使用说明

支持的路由

| 页面 | 路由url | 备注 |
| ------ | ------ | ------ |
| 首页弹窗页面 | hmiou://m.54jietiao.com/homedialog<br/>?dialog_type=*<br/>&dialog_title=<br/>&dialog_content=<br/>&dialog_sub_content=<br/>&dialog_file_down_url=<br/>&dialog_id=<br/>&dialog_ad_image_ur=<br/>&dialog_ad_link_url=<br/>&notice_id=<br/>&notice_push_time=|dialog_type是一个DialogType的枚举类型，("1", "官方私信"；"2", "重要升级"；"3", "发现新版本"，"4", "活动广告(红包)"，"5", "活动广告(其他)"，"100", "官方公告")<br/>dialog_content是软件更新提醒弹窗的正文内容<br/>dialog_sub_content是软件更新提醒弹窗的次级正文内容<br/>dialog_id是广告弹窗的唯一id<br/>dialog_ad_image_ur是广告弹窗的广告图片地址<br/>dialog_ad_link_url是广告弹窗点击之后具体的活动链接地址<br/>notice_id是系统公告的唯一id<br/>notice_push_time是系统公告的推送时间<br/>用户点击系统公告的最小化按钮，会把系统公告插入消息中心，并发送key为"HomeDialog_moveNoticeToMsgCenter"，content为"官方公告成功插入到消息中心"的CommBizEvent事件|

路由文件

```json
{
  "homedialog": [
    {
      "url": "hmiou://m.54jietiao.com/homedialog?dialog_type=*&dialog_title=&dialog_content=&dialog_sub_content=&dialog_file_down_url=&dialog_id=&dialog_ad_image_ur=&dialog_ad_link_url=&notice_id=&notice_push_time=",
      "iclass": "",
      "aclass": "com.hm.iou.homedialog.business.HomeDialogActivity"
    }
  ]
}
```

#### 集成说明

- 集成本模块之前，需要保证一下模块已经初始化：

Logger（日志记录），HM-BaseBiz初始化(基础业务模块)，HM-Network（网络框架），HM-Router（路由模块）

#### Author

syl