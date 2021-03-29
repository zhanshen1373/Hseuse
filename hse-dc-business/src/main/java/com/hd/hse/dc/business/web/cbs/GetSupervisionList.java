package com.hd.hse.dc.business.web.cbs;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainPCDataListener;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetSupervisionList extends GainPCDataListener {
    private static Logger logger = LogUtils.getLogger(PCGetRulesRecords.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    private String personid = null;
    private ArrayList<WorkLogEntry> mWorkLogEntrys = new ArrayList<WorkLogEntry>();

    public GetSupervisionList() {
        personid = SystemProperty.getSystemProperty().getLoginPerson()
                .getPersonid();
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        try {
            super.action(action, args);
            this.sendMessage(IMessageWhat.END, 100, 100, "", mWorkLogEntrys);
        } catch (HDException e) {
            this.sendMessage(IMessageWhat.ERROR, 50, 50, e.getMessage(), null);
        }
        return mWorkLogEntrys;
    }

    @Override
    public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
        super.afterDataInfo(pcdata, obj);
        // TODO Auto-generated method stub
        //logger.error("list"+pcdata.toString());
        try {
            JSONObject jSONObject = new JSONObject(pcdata.toString());
//			logger.error(pcdata.toString(), null);
            JSONArray mJSONArray = jSONObject.optJSONArray("UD_CBSGL_RZ");
            if (mJSONArray == null) {
                return;
            }
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject mJSONObject = mJSONArray.getJSONObject(i);
                WorkLogEntry mWorkLog = new WorkLogEntry();

                mWorkLog.setUd_cbsgl_rzid(mJSONObject.optString("ud_cbsgl_rzid"));
                mWorkLog.setPadrzid(mJSONObject.optString("padprimarykey"));
                //检查单位
                mWorkLog.setJcdept_desc(mJSONObject.optString("jcdept_desc"));
                mWorkLog.setJcdept(mJSONObject.optString("jcdept"));
                //检查人
                mWorkLog.setCreatebydesc(mJSONObject.optString("inspector_desc"));
                mWorkLog.setCreateby(mJSONObject.optString("createby"));
                //检查时间
                mWorkLog.setCreatedate(mJSONObject.optString("createdate"));
                //作业票
                mWorkLog.setZyname(mJSONObject.optString("zyname"));
                mWorkLog.setUd_zyxk_zysqid(mJSONObject.optString("ud_zyxk_zysqid"));
                //属地部门
                mWorkLog.setZyarea_desc(mJSONObject.optString("zyarea_desc"));
                mWorkLog.setZyarea(mJSONObject.optString("zyarea"));
                //属地单位
                mWorkLog.setSddept_desc(mJSONObject.optString("sddept_desc"));
                mWorkLog.setSddept(mJSONObject.optString("sddept"));
                //问题分类
                mWorkLog.setRztype(mJSONObject.optString("rztype"));
                //问题描述
                mWorkLog.setFxwt(mJSONObject.optString("fxwt"));
                //现场负责人
                mWorkLog.setFzperson_desc(mJSONObject.optString("fzperson_desc"));
                mWorkLog.setFzperson(mJSONObject.optString("fzperson"));
                //违章人员
                mWorkLog.setRulesdescription(mJSONObject.optString("rulesdescription"));
                mWorkLog.setInnercard(mJSONObject.optString("innercard"));
                //责任单位
                mWorkLog.setZrdept_desc(mJSONObject.optString("zrdept_desc"));
                mWorkLog.setZrdept(mJSONObject.optString("zrdept"));
                //罚款金额
                mWorkLog.setWyj(mJSONObject.optString("wyj"));
                //是否整改
                mWorkLog.setIszg(Integer.parseInt(mJSONObject.optString("iszg")));
                //转人员黑名单
                mWorkLog.setIshmd(Integer.parseInt(mJSONObject.optString("ishmd")));
                //禁入原因
                mWorkLog.setRejectreason(mJSONObject.optString("rejectreason"));
                //录入备注
                mWorkLog.setRemarks(mJSONObject.optString("remarks"));

                //风险级别
                mWorkLog.setFxjb(mJSONObject.optString("fxjb"));
                //违章标准id
                mWorkLog.setUd_cbsgl_khbzid(mJSONObject.optString("ud_cbsgl_khbzid"));
                //违章风险指
                mWorkLog.setWzfxz(mJSONObject.optInt("wzfxz"));
                //违章类别
                mWorkLog.setWzlb(mJSONObject.optString("wzlb"));
                //违章条款
                mWorkLog.setWztk(mJSONObject.optString("wztk"));
                //是否开具考核单
                mWorkLog.setExamyj(mJSONObject.optString("examyj"));
                //考核标准依据
                mWorkLog.setIskhd(mJSONObject.optInt("iskhd"));
                //限制整改日期
                mWorkLog.setReform_date(mJSONObject.optString("reform_date"));
                //是否典型问题
                mWorkLog.setIstypicissue(mJSONObject.optInt("istypicissue"));
                //扣分
                mWorkLog.setPenaltypoint(mJSONObject.optString("penaltypoint"));
                //整改措施
                mWorkLog.setZgcs(mJSONObject.optString("zgcs"));
                //整改验证结论
                mWorkLog.setZgyzjl(mJSONObject.optString("zgyzjl"));
                //是否验收
                mWorkLog.setIsys(mJSONObject.optInt("isys"));
                //检查地点
                mWorkLog.setBreachlocation(mJSONObject.optString("breachlocation"));

                mWorkLogEntrys.add(mWorkLog);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object initParam() {

        // hashmap.put(PadInterfaceRequest.KEYFUNCODE, value);
        hashmap.put(PadInterfaceRequest.KEYPERSONID, personid);
        return hashmap;
    }

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public String getMethodType() {
        // TODO Auto-generated method stub
        return PadInterfaceContainers.GET_ENDRLIST_BY_INSPECTOR;
    }

}
