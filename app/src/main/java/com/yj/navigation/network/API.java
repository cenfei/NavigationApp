package com.yj.navigation.network;

/**
 * 功能：全局变量
 * <p/>
 * /**
 *
 * @author pc
 */
public interface API {
    /*********************************
     * URL变量
     *****************************************/
    public String server = "http://114.55.144.81:8081/uc";

    public String serverAvar = "http://114.55.144.81:8082/img";
    public String serverApp = "http://114.55.144.81:8081/app";

    public String serverDeviceAvar = "http://114.55.144.81:8081/app";
    public String serverMsg = "http://114.55.144.81:8082/msg";



    /**
     * 获取手机验证码
     */
    public String GET_MOBILE_MSG_URL = server + "/user/reg/getVCode";


    /**
     * 获取注册
     */
    public String REGISTER_URL = server + "/user/reg/auth";


    /**
     * 注册--完善信息
     */
    public String REGISTER_COMPLETE_URL = server + "/user/reg/register";



    /**
     * 登录
     */
    public String LOGIN_BY_PWD_URL = server + "/user/login/loginByPassword";



    /**
     * 注销
     */
    public String LOGIN_OUT_URL = server + "/user/login/logout";



    /**
     * 忘记密码---第一步发送手机验证码
     */
    public String FORGET_GETCODE_URL = server + "/user/loginPwd/getVCode";


    /**
     * 忘记密码---第二步验证手机验证码
     */
    public String FORGET_UPDATE_URL = server + "/user/loginPwd/authVCode";



    /**
     * 忘记密码---第三步  重设密码
     */
    public String FORGET_RESET_URL = server + "/user/loginPwd/reset";


    /**
     * 查询密保问题列表
     */
    public String QUERY_QUESTION_URL = server + "/user/secretquestion/query";

    /**
     * 设置密保问题列表
     */
    public String UPDATE_QUESTION_URL = server + "/user/secretquestion/add";



    /**
     * 验证问题列表
     */
    public String CHECK_QUESTION_URL = server + "/user/secretquestion/check";



    /**
     * -更新安全密码
     */
    public String UPDATE_PWD_URL = server + "/user/securityPwd/check";


    /**
     * -设置安全密码
     */
    public String SET_PWD_URL = server + "/user/securityPwd/save";


    /**
     * -更新安全密码
     */
    public String RESET_PWD_URL = server + "/user/securityPwd/updateBySecquestion";





    /**
     * -更新安全密码--通过旧密码
     */
    public String RESET_PWD_WITHOLDER_URL = server + "/user/securityPwd/updateByOldpwd";



    /**
     * 更新用户信息
     */
    public String UPDATE_USERINFO_URL = server + "/user/info/update";










    /**
     * 绑定银行卡
     */
    public String BIND_CARD_URL = serverApp + "/acct/bankcard/bind";



    /**
     * 解绑银行卡
     */
    public String UNBIND_CARD_URL = serverApp + "/acct/bankcard/unbind";

    /**
     * 银行卡列表
     */
    public String MY_CARDLIST_URL = serverApp + "/acct/bankcard/list";

    /**
     * 银行列表
     */
    public String MY_BANKLIST_URL = serverApp + "/acct/bank/list";


    /**
     * 账户信息
     */
    public String QUERY_ACCOUNT_URL = serverApp + "/acct/query/info";


    /**
     * 账户详细信息
     */
    public String MY_ACCOUNT_DETAIL_LIST_URL = serverApp + "/acct/query/exchangeList";






    /**
     * 上传头像
     */
    public String UPLOAD_AVAR_URL = serverAvar + "/image/load/app";



    /**
     * 绑定设备
     */
    public String BIND_DEVICE_URL = serverDeviceAvar + "/dev/manager/bind";


    /**
     * 解绑设备
     */
    public String UNBIND_DEVICE_URL = serverDeviceAvar + "/dev/manager/unbind";



    /**
     * 设备列表
     */
    public String DEVICE_LIST_URL = serverDeviceAvar + "/dev/manager/list";


    /**
     * 我的工单列表
     */
    public String MY_JOB_LIST_URL = serverDeviceAvar + "/job/order/list";

    /**
     * 我的视频列表
     */
    public String MY_Video_LIST_URL = serverDeviceAvar + "/job/video/list";




    /**
     * 我的详情
     */
    public String MY_JOB_DETAIL_URL = serverDeviceAvar + "/job/order/detail";



    /**
     * 我的事故列表
     */
    public String MY_ACCIDENT_LIST_URL = serverDeviceAvar + "/job/accident/list";

    /**
     * 我的事故详情
     */
    public String MY_ACCIDENT_DETAIL_URL = serverDeviceAvar + "/job/accident/detail";


    /**
     *完善工单
     */
    public String UPDATE_JOB_INFO = serverDeviceAvar + "/job/order/perfect";

    /**
     *再次举报  ---从视频列表
     */
    public String UPDATE_AGIAN_JOB_INFO = serverDeviceAvar + "/job/video/report";


    /**
     * 首页账户预览
     */
    public String ACCT_QUERY_INFO = serverApp + "/index/overview";



    /**
     *更新系统
     */
    public String UPDATE_VERSION_INFO = serverDeviceAvar + "/index/update";


    /**
     * 预约  从库id
     */
    public String ORDRE_DESIGN_FROM_LIB = server + "order/make";





    //消息列表

    public String MESSAGE_LIST = serverMsg + "/pushusercount/getLastMsg";


    public String MESSAGE_Detail = serverMsg + "/msgrecod/getMsgByType";
    public String MESSAGE_Update_Read = serverMsg + "/pushusercount/updateCount";











    public String BANK_LIST_URL = serverApp + "/acct/bankcard/list";



}