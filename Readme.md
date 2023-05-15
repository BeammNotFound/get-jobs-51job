<h1 align="center"> get-jobs-51job</h1>
<div align="center">
    💼在前程无忧自动投递简历
</div>

## 如何使用
由于使用selenium，所以
### 第一步：下载ChromeDriver
找到你对应Chrome版本的ChromeDriver
> https://sites.google.com/a/chromium.org/chromedriver/home

### 第二步：配置ChromeDriver
```java
        System.setProperty("webdriver.chrome.driver", "/Users/beamstark/Desktop/chromedriver");
```
由于我是系统全局配置，所以这行代码没有在代码块中出现，需要的小伙伴自行添加代码配置

### 第三步：修改代码
#### 修改主程序代码
打开 /src/main/java/ResumeSubmission.java <br>
**page** ：从第几页开始投递，page不能小于1<br>
**maxPage**：投递到第几页<br>
**EnableNotifications**：是否开启Telegram机器人通知
#### 修改TelegramBot通知代码
打开 /src/main/java/utils/TelegramNotificationBot.java <br>
**TELEGRAM_API_TOKEN** ：你的机器人的token <br>
**CHAT_ID**：你的机器人的chat_id <br>
### 最后一步：运行代码
需要扫码登陆
<br>
****

**如果你想在 [拉钩网](http://lagou.com) 上自动投递简历，请 [Click me](https://github.com/BeammNotFound/get-jobs-lagou).**
<br>
**如果你想在 [Boss直聘](http://zhipin.com) 上自动投递简历，请 [Click me](https://github.com/BeammNotFound/get-jobs-boss).**
<br>

> 希望能够在现在的大环境下帮助你找到一份满意的工作

## 请我喝杯咖啡☕️
<img src="./src/public/IMG_6480.JPG" alt="" width="300">

<img src="./src/public/IMG_6479.JPG" alt="" width="300">
