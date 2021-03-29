package com.hd.hse.dc.business.listener;

import android.os.Message;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.web.zyxk.GainDetailSearchList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by dubojian on 2017/8/24.
 */

public class GetDetailSearchWorkListListener extends AbstractActionListener {

    // 作业票下载浏览标记
    private boolean browseFlag = false;
    private static Logger logger = LogUtils
            .getLogger(GetListActionListener.class);
    private List<SuperEntity> listsuper = null;

    public GetDetailSearchWorkListListener(boolean typeBoolean) {
        browseFlag = typeBoolean;
    };
    public GetDetailSearchWorkListListener() {

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
    private List<SuperEntity> getDownList(Object... args) {
        // PCGetZYXKDataList getList = null;
        // getList=new PCGetZYXKDataList();
        GainDetailSearchList gainDetailSearchList = new GainDetailSearchList();
        try {
            if (browseFlag) {
                // 作业票浏览
                gainDetailSearchList.setBrowseFlag(true);
            }
            if (args != null && args.length > 0) {
                gainDetailSearchList.setSearch((String)args[0]);
            }
            listsuper = (List<SuperEntity>) gainDetailSearchList.action("", args);
            sendAsyncLoginSusMsg();
        } catch (AppException e) {
            sendAsyncErrMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("获取作业票信息报错:" + e.getMessage(), e);
            sendAsyncErrMsg(e.getMessage());
        }

        return listsuper;
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
        retMsg.setReturnResult(listsuper);
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
