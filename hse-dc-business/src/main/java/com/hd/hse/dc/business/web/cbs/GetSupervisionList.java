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
                //????????????
                mWorkLog.setJcdept_desc(mJSONObject.optString("jcdept_desc"));
                mWorkLog.setJcdept(mJSONObject.optString("jcdept"));
                //?????????
                mWorkLog.setCreatebydesc(mJSONObject.optString("inspector_desc"));
                mWorkLog.setCreateby(mJSONObject.optString("createby"));
                //????????????
                mWorkLog.setCreatedate(mJSONObject.optString("createdate"));
                //?????????
                mWorkLog.setZyname(mJSONObject.optString("zyname"));
                mWorkLog.setUd_zyxk_zysqid(mJSONObject.optString("ud_zyxk_zysqid"));
                //????????????
                mWorkLog.setZyarea_desc(mJSONObject.optString("zyarea_desc"));
                mWorkLog.setZyarea(mJSONObject.optString("zyarea"));
                //????????????
                mWorkLog.setSddept_desc(mJSONObject.optString("sddept_desc"));
                mWorkLog.setSddept(mJSONObject.optString("sddept"));
                //????????????
                mWorkLog.setRztype(mJSONObject.optString("rztype"));
                //????????????
                mWorkLog.setFxwt(mJSONObject.optString("fxwt"));
                //???????????????
                mWorkLog.setFzperson_desc(mJSONObject.optString("fzperson_desc"));
                mWorkLog.setFzperson(mJSONObject.optString("fzperson"));
                //????????????
                mWorkLog.setRulesdescription(mJSONObject.optString("rulesdescription"));
                mWorkLog.setInnercard(mJSONObject.optString("innercard"));
                //????????????
                mWorkLog.setZrdept_desc(mJSONObject.optString("zrdept_desc"));
                mWorkLog.setZrdept(mJSONObject.optString("zrdept"));
                //????????????
                mWorkLog.setWyj(mJSONObject.optString("wyj"));
                //????????????
                mWorkLog.setIszg(Integer.parseInt(mJSONObject.optString("iszg")));
                //??????????????????
                mWorkLog.setIshmd(Integer.parseInt(mJSONObject.optString("ishmd")));
                //????????????
                mWorkLog.setRejectreason(mJSONObject.optString("rejectreason"));
                //????????????
                mWorkLog.setRemarks(mJSONObject.optString("remarks"));

                //????????????
                mWorkLog.setFxjb(mJSONObject.optString("fxjb"));
                //????????????id
                mWorkLog.setUd_cbsgl_khbzid(mJSONObject.optString("ud_cbsgl_khbzid"));
                //???????????????
                mWorkLog.setWzfxz(mJSONObject.optInt("wzfxz"));
                //????????????
                mWorkLog.setWzlb(mJSONObject.optString("wzlb"));
                //????????????
                mWorkLog.setWztk(mJSONObject.optString("wztk"));
                //?????????????????????
                mWorkLog.setExamyj(mJSONObject.optString("examyj"));
                //??????????????????
                mWorkLog.setIskhd(mJSONObject.optInt("iskhd"));
                //??????????????????
                mWorkLog.setReform_date(mJSONObject.optString("reform_date"));
                //??????????????????
                mWorkLog.setIstypicissue(mJSONObject.optInt("istypicissue"));
                //??????
                mWorkLog.setPenaltypoint(mJSONObject.optString("penaltypoint"));
                //????????????
                mWorkLog.setZgcs(mJSONObject.optString("zgcs"));
                //??????????????????
                mWorkLog.setZgyzjl(mJSONObject.optString("zgyzjl"));
                //????????????
                mWorkLog.setIsys(mJSONObject.optInt("isys"));
                //????????????
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
