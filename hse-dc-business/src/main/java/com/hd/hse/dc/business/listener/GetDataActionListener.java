package com.hd.hse.dc.business.listener;

import android.os.Message;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.web.zyxk.GainChartData;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Created by dubojian on 2017/7/21.
 */

public class GetDataActionListener extends AbstractActionListener {

    private static Logger logger = LogUtils
            .getLogger(GetListActionListener.class);
    private Object accepctData;
    private String pageType;
    //    private List<SuperEntity> listsuper = null;


    public GetDataActionListener(String pt) {
        this.pageType=pt;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        if (StringUtils.isEmpty(action)) {
            return getDownList(args);
        }
        return super.action(action);
    }

    /**
     * getDownList:(获取下载作业票列表). <br/>
     * date: 2014年10月8日 <br/>
     *
     * @author lxf
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    private Object getDownList(Object... args) {
        // PCGetZYXKDataList getList = null;
        // getList=new PCGetZYXKDataList();
        GainChartData chartData=new GainChartData();
        try {
            if (pageType!=null){
                chartData.setPageType(pageType);
            }
            if (args != null && args.length > 0) {
                chartData.setSearch(args[0].toString());
            }
            accepctData = chartData.action("", args);
//            listsuper = (List<SuperEntity>) chartData.action("", args);
            sendAsyncLoginSusMsg();
        } catch (AppException e) {
            sendAsyncErrMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("获取大连首页信息出错:" + e.getMessage(), e);
            sendAsyncErrMsg(e.getMessage());
        }

        return accepctData;
    }

    /**
     * sendAsyncProcessingMsg:(查看列表等待消息). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author lxf
     * @param msg
     */
    private void sendAsyncProcessingMsg(String msg) {
        Message message = getAsyncTask().obtainMessage();
        message.what = IMessageWhat.PROCESSING;
        // message.getData().putString(IActionType.LOGIN_LOGIN, msg);
        AysncTaskMessage retMsg = new AysncTaskMessage();
        retMsg.setMessage(msg);
        message.getData().putSerializable("p", retMsg);

        getAsyncTask().sendMessage(message);
    }

    /**
     * sendAsyncSusMsg:(获取数据成功后发送消息). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author lxf
     */
    private void sendAsyncLoginSusMsg() {
        Message message = getAsyncTask().obtainMessage();
        message.what = IMessageWhat.END;
        AysncTaskMessage retMsg = new AysncTaskMessage();
        retMsg.setReturnResult(accepctData);
        message.getData().putSerializable("p", retMsg);
        getAsyncTask().sendMessage(message);
    }

    /**
     * sendAsyncErrMsg:(发送异步错误消息). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author lxf
     * @param msg
     */
    private void sendAsyncErrMsg(String msg) {
        Message message = getAsyncTask().obtainMessage();
        message.what = IMessageWhat.ERROR;
        // message.getData().putString(IActionType.LOGIN_LOGIN, msg);
        AysncTaskMessage retMsg = new AysncTaskMessage();
        retMsg.setMessage(msg);
        message.getData().putSerializable("p", retMsg);
        getAsyncTask().sendMessage(message);
    }




}
