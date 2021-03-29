package com.hd.hse.padinterface;

/**
 * ClassName: PadInterfaceRequest (交互请求参数编码)<br/>
 * date: 2014年9月16日 <br/>
 *
 * @author lxf
 */
public class PadInterfaceRequest {
    /**
     * PERSONID:TODO(登录用户编码).
     */
    public final static String KEYPERSONID = "personid";

    /**
     * PERSONID:TODO(辽阳独有的).
     */
    public final static String LOGINFROM = "loginfrom";

    /**
     * PERSONID:TODO(登录用户描述).
     */
    public final static String KEYPERSONDESC = "persondesc";

    /**
     * DEPTNUM:TODO(登录用户部门).
     */
    public final static String KEYDEPTNUM = "department";
    /**
     * 报表的穿透
     */
    public final static String KEYCHUANTOU = "zydept";

    /**
     * 区分是管理人员还是使用人员
     */
    public final static String KEYPAGETYPE = "pagetype";

    /**
     * 使用人员的部门
     */
    public final static String KEYDEPARTMENT = "department";

    /**
     * DEPTNUM:TODO(登录用户部门).
     */
    public final static String KEYDEPTDESC = "department_desc";

    /**
     * TABLE:TODO(用户记录表明+时间戳的Key).
     */
    public final static String KEYTABLE = "table";

    /**
     * KEYDATA:TODO(上传内容的key).
     */
    public final static String KEYDATA = "data";

    /**
     * PADVERSION:TODO(版本号).
     */
    public final static String KEYPADVERSION = "padversion";
    /**
     * 服务器时间
     */
    public final static String KEYSERVERDATE = "serverdate";

    /**
     * PADAPKURL:TODO(升级地址).
     */
    public final static String KEYPADAPKURL = "padapkurl";

    /**
     * KEYFILENAME:TODO(上传文件名称编码).
     */
    public final static String KEYFILENAME = "filename";
    /**
     * KEYAPPNAME:TODO(承包商下载照片时,appname).
     */
    public final static String KEYAPPNAME = "appname";

    /**
     * KEYTABLIEID:TODO(表唯一id).
     */
    public final static String KEYTABLIEID = "uniqueid";

    /**
     * KEYRETURNID:TODO(pc端返回信息用到的主键).
     */
    public final static String KEYRETURNID = "id";

    /**
     * KEYRETURNMESSAGE:TODO(pc端返回信息用到的msg).
     */
    public final static String KEYRETURNMESSAGE = "mesg";

    /**
     * KEYZYPSTRID:TODO(作业票ID字符串).
     */
    public final static String KEYZYPSTRID = "zypstr";

    public final static String TABLENAME = "tablename";

    public final static String TABLEID = "tableid";

    public final static String CHILDIDS = "childids";

    public final static String UPLOADPICTUREID="ud_zyxk_car_xkzid";


    public final static String STARTTIME="starttime";
    public final static String ENDTIME="endtime";
    public final static String CARNUMBER="carnumber";
    /**
     * KEYWHERE:TODO(条件).
     */
    public final static String KEYWHERE = "where";

    /**
     * TICKETCHECK:TODO(作业票检查的条件)
     */
    public final static String TICKETCHECK = "ticketcheck";
    /**
     * KEYZYPCLASS:TODO(作业票类别).
     */
    public final static String KEYZYPCLASS = "zypclass";
    /**
     * KEYZYPLEVEL:TODO(作业票等级).
     */
    public final static String KEYZYPLEVEL = "zyplevel";
    /**
     * KEYZYPID:TODO(作业票主键).
     */
    public final static String KEYZYPID = "zypid";

    /**
     * KEYALERTCODE:TODO(待办事项中，数据集的主键).
     */
    public final static String KEYALERTCODE = "alertcode";

    /**
     * KEYMAC:TODO(硬件MAC地址).
     */
    public final static String KEYMAC = "pdamac";
    /**
     * 硬件imei
     */
    public final static String KEYIMEI = "imei";

    /**
     * KEYZYPSTRID:TODO(作业票ID字符串).
     */
    public final static String KEYEXCLUDEZYPSTRID = "excludezypstr";
    /**
     * KEYCLUDEZYPCLASSSTR:TODO(作业票类型串).
     */
    public final static String KEYCLUDEZYPCLASSSTR = "keycludezypclassstr";

    /**
     * KEYPASSWORD:TODO(人员卡人员密码).
     */
    public final static String KEYPASSWORD = "password";

    /**
     * KEYPASSWORD:TODO(承包商人员入厂证号).
     */
    public final static String KEYINNERCARD = "innercard";
    /**
     * KEYPASSWORD:TODO(承包商人员身份证号).
     */
    public final static String KEYICCARD = "iccard";

    /**
     * KEYCREATETIME:TODO(创建时间key).
     */
    public final static String KEYCREATETIME = "createtime";

    /**
     * KEYFUNCODE:TODO(功能编码key).
     */
    public final static String KEYFUNCODE = "funcode";

    /**
     * 摄像头接口的位置
     */
    public final static String LOCATIONS="locations";

    public final static String OPENCAMERAS="openCamera";

    public final static String CLOSECAMERAS="closeCamera";

    public final static String RTSP="rtsp";



    /**
     * KEYUPLOADFILE:TODO(上传文件).
     */
    public final static String KEYUPLOADFILE = "uploadfile";

    /**
     * KEYNEWSSERVERURL:TODO(消息服务的URLKEY).
     */
    public final static String KEYNEWSSERVERURL = "newsserverurl";

    /**
     * KEYUUID:TODO(全球唯一ID).
     */
    public final static String KEYUUID = "uuid";

    /**
     * KEY_MSG_STATUS(消息状态 1-未读 2-已读).
     */
    public final static String KEY_MSG_STATUS = "msgstatus";

    /**
     * KEY_MSG_TYPE(消息类型 -QTJC CQGB)
     */
    public final static String KEY_MSG_TYPE = "msgtype";

    /**
     * KEY_START_TIME(过滤开始时间 yyyy-MM-dd hh:mm:ss)
     */
    public final static String KEY_START_TIME = "starttime";

    /**
     * KEY_MSG_ID(推送消息主键ID).
     */
    public final static String KEY_MSG_ID = "msgid";

    /**
     * KEY_URL
     */
    public final static String KEY_URL = "url";

    /**
     * KEY_NAME
     */
    public final static String KEY_NAME = "name";
    /**
     * 日志id
     */
    public final static String KEY_RZID = "rzid";
    /**
     * 身份证
     */
    public final static String KEY_IDCARD = "idcard";
    /**
     * 证书编码
     */
    public final static String KEY_ZSNUM = "zsnum";
    public final static String KEY_UDCBSGLRYTZZYID = "ud_cbsgl_rytzzyid";
    public final static String KEY_ZCPROJECT = "ZCPROJECT";
}
