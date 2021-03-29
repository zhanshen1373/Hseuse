package com.hd.hse.padinterface;

public class PadInterfaceContainers {


    /**
     * METHOD_SUCCESS:TODO(执行结果 T).
     */
    public final static String METHOD_SUCCESS = "T";
    /**
     * METHOD_FAILD:TODO(执行结果 F).
     */
    public final static String METHOD_FAILD = "F";
    /**
     * V_ACTION_INSERT:TODO(数据处理动作 insert).
     */
    public final static String V_ACTION_INSERT = "INSERT";
    /**
     * V_ACTION_UPDATE:TODO(数据处理动作 update).
     */
    public final static String V_ACTION_UPDATE = "UPDATE";

    /**
     *	 接口动作，PC端系统实现对应业务
     */

    /**
     * METHOD_CBS_BASICDOWN:TODO(下载：承包商基础数据).
     */
    public final static String METHOD_CBS_BASICDOWN = "CBS_001";
    /**
     * METHOD_CBS_PERSIONDOWN:TODO(下载：承包商人员及特种作业证).
     */
    public final static String METHOD_CBS_PERSIONDOWN = "CBS_002";
    /**
     * METHOD_CBS_CARDOWN:TODO(下载：承包商入厂车辆及驾驶员).
     */
    public final static String METHOD_CBS_CARDOWN = "CBS_003";

    /**
     * METHOD_CBS_WORKLOGUP:TODO(上传：承包商工作日志).
     */
    public final static String METHOD_CBS_WORKLOGUP = "CBS_004";

    /******************** 下载：承包商人员图片查看******************** */
    /**
     * METHOD_CBS_CBSHEADIAMGEURL:TODO(获取承包商人员头像照片路径).
     */
    public final static String METHOD_CBS_CBSHEADIAMGEURL = "CBS_005";

    /**
     * METHOD_CBS_CBSINANDOUTFACTORYRECORDS:TODO(承包商人员出入厂记录).
     */
    public final static String METHOD_CBS_CBSINANDOUTFACTORYRECORDS = "CBS_006";

    /**
     * METHOD_CBS_CBSINANDOUTFACTORYRECORDS:TODO(承包商人员违章记录).
     */
    public final static String METHOD_CBS_CBSRULESRECORDS = "CBS_007";

    /**
     * METHOD_CBS_CBSCULTIVATERECORDS:TODO(承包商人员培训记录).
     */
    public final static String METHOD_CBS_CBSCULTIVATERECORDS = "CBS_008";

    /**
     * METHOD_COMMON_DOWNTABLE:TODO(通用：下载表信息).
     */
    public final static String METHOD_COMMON_DOWNTABLE = "COMMON_001";
    /**
     * METHOD_COMMON_DOWNFILE:TODO(通用：下载文件).
     */
    public final static String METHOD_COMMON_DOWNFILE = "COMMON_002";
    /**
     * METHOD_COMMON_UPFILE:TODO(通用：上传文件).
     */
    public final static String METHOD_COMMON_UPFILE = "COMMON_003";
    /**
     * METHOD_COMMON_UPTABLE:TODO(通用：上传表信息).
     */
    public final static String METHOD_COMMON_UPTABLE = "COMMON_004";

    /**
     * 上传车辆许可证
     */
    public final static String METHOD_CAR_XKZ = "XKZ_002";


    /******************** 通用：获得文件大小*********************/
    public final static String METHOD_COMMON_101 = "COMMON_101";
    /**
     * METHOD_COMMON_CHECKUSER:TODO(通用：登陆验证).
     */
    public final static String METHOD_COMMON_CHECKUSER = "COMMON_102";
    /**
     * METHOD_COMMON_VERSIONIFNO:TODO(通用：获取版本信息).
     */
    public final static String METHOD_COMMON_VERSIONIFNO = "COMMON_103";
    /**
     * 得到大连的首页图表信息
     */
    public final static String GET_DALIAN_TUBIAO_MESSAGE = "ZYP_012";

    public final static String METHOD_CAR_XKZ_LIST = "XKZ_001";
    /******************** 下载：作业票列表*********************/
    public final static String METHOD_ZYP_LIST = "ZYP_001";//作业票列表
    /******************** 下载：作业票信息*********************/
    public final static String METHOD_ZYP_DOWN = "ZYP_002";//作业票信息
    /******************** 下载：返回下载成功的信息*********************/
    public final static String METHOD_ZYP_DOWNRETURN = "ZYP_003";//返回下载成功的信息
    /******************** 下载：获得作业票的状态值*********************/
    public final static String METHOD_ZYP_GETSTATUS = "ZYP_004";//获得作业票的状态值
    /******************** 下载：获得作业票的状态值*********************/
    public final static String METHOD_ZYP_GETTZPSTATUS = "ZYP_005";//获得作业票的状态值
    /******************** 下载：获得特种作业票的状态值*********************/
    public final static String METHOD_ZYP_GETLSYDSTATUS = "ZYP_006";//获得临时用电票的状态值
    /******************** 下载：按作业票id获得票的实际结束时间*********************/
    public final static String METHOD_ZYP_GETSJENDTIME = "ZYP_007";//获得动火票

    public final static String METHOD_ZYP_DETAILSEARCH = "ZYP_013"; //作业票详细搜索

    /******************** 列表加载--获取当日作业票的所有作业票*********************/
    public final static String METHOD_ZYP_TODAY_WORKLIST = "ZYP_017";//获取当日作业票的所有作业票
    /******************** 列表加载--获取待办事项列表*********************/
    public final static String METHOD_ZYP_WAITE_WORKLIST = "ZYP_018";//获取待办事项列表
    /******************** 状态修改--更改待办事项的状态*********************/
    public final static String METHOD_ZYP_UPDATE_WAITE_WORK_STATUS = "ZYP_019";//更改待办事项的状态


    /**
     * METHOD_ZYP_DOWNQTJC:TODO(同步气体检测).
     */
    public final static String METHOD_ZYP_DOWNQTJC = "ZYP_008";//同步气体检测

    /**
     * METHOD_ZYP_GETLISTP:TODO(获取APPAUDITED 状态的作业票).
     */
    public final static String METHOD_ZYP_GETLISTSTATUS = "ZYP_009";//获取APPAUDITED 状态的作业票

    /**
     * METHOD_ZYP_ONLINECONFIRM:TODO(最终审核人在线确认).
     */
    public final static String METHOD_ZYP_ONLINECONFIRM = "ZYP_010";

    /**
     * METHOD_ZYP_ONLINENULLIFY:TODO(最终审核人在线作废).
     */
    public final static String METHOD_ZYP_ONLINENULLIFY = "ZYP_011";


    /**
     * METHOD_COMMON_TEST:TODO(校验网络是否连接成功).
     */
    public final static String METHOD_COMMON_TEST = "COMMON_104";

    /**
     * METHOD_COMMON_TEST:TODO(特殊基础数据更新，目前只更新url).
     */
    public final static String METHOD_COMMON_SPECIALUPDATE = "COMMON_105";

    /**
     * METHOD_COMMON_TEST:TODO( 获取图片路径url).
     */
    public final static String METHOD_COMMON_GETIMAGEPATH = "COMMON_106";

    /**
     * METHOD_COMMON_GETNEWSSERVERURL:TODO(获取推送服务器的url).
     */
    public final static String METHOD_COMMON_GETNEWSSERVERURL = "COMMON_108";

    /**
     * METHOD_ZYPONLINE_NEWSLIST:TODO(获取推送消息列表).
     */
    public final static String METHOD_ZYPONLINE_NEWSLIST = "ZYPONLINE_008";

    /**
     * 通知消息已读.
     */
    public final static String METHOD_COMMON_MSG_ISREAD = "COMMON_109";

    /**
     * 获取附件PDF的URL地址
     */
    public final static String GET_PDF = "COMMON_110";
    /**
     * 获取路线图的URL地址
     */
    public final static String GET_ROUTE = "COMMON_112";
    /**
     * 获取证书图片
     */
    public final static String GET_CERTIFICATE_IMG = "CBS_009";
    /**
     * 下载：人员违章次数统计
     * Created by liuyang on 2016年11月17日
     */
    public final static String GET_CBSRULESRECORDS_COUNT = "CBS_012";
    /**
     * 下载：承包商合同条款
     * Created by liuyang on 2016年11月17日
     */
    public final static String GET_CBS_HTTK = "CBS_013";
    /**
     * 获取日志列表
     */
    public final static String GET_ENDRLIST_BY_INSPECTOR = "CBS_010";
    /**
     * 获取日志详细信息
     */
    public final static String GET_ENDR_BY_INSPECTOR = "CBS_011";
    /**
     * 获取现场监督后台图片接口
     */
    public final static String GET_XCJD_PIC = "COMMON_107";

    public final static String METHOD_CBS_CBSRULESRECORDS_COUNT = "CBS_012";
    public final static String METHOD_CBS_TRAININGRECORDS = "CBS_013";

    /**
     * 获取远程审批信息
     */
    public final static String GET_REMOTE_APPR = "COMMON_113";

    /**
     * 校验远程审批环节点
     */
    public final static String CHECK_REMOTE_APPR = "REMOTE_001";
    /**
     * 远程审批不同意
     */
    public final static String REMOTE_APPR_DISAGREE = "REMOTE_002";
    /**
     * 获取监护人成绩接口
     */
    public final static String JHR_KSCJ = "CBS_014";
}
