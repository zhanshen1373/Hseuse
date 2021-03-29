package com.hd.hse.common.module.phone.business.readcard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hd.hse.business.util.SYSConstant;

import org.apache.commons.lang.StringUtils;

public class ReadCardThread extends Thread {

    private Handler myhandler;
    private boolean isexit = false;
    private static int times = 0;

    public void setStop() {
        this.isexit = true;
    }

    public void setStart() {
        this.isexit = false;
    }

    public ReadCardThread(Handler handler) {
        myhandler = handler;
    }

    @Override
    public void run() {
        while (!isexit) {
            //Log.e("lxf", "读卡");
            String cardcode = null;
            try {
                Thread.sleep(100);
                times++;
                //可以设置循环次数 设置默认值
//				if(times>(10*5))
//				{
//					times=0;
//					cardcode="12345678";
//					isexit=true;
//					Bundle bd = new Bundle();
//					bd.putString(SYSConstant.KEYCARDCODE, cardcode);
//					Message msg =myhandler.obtainMessage();// new Message();
//					msg.setData(bd);
//					myhandler.sendMessage(msg);
//					return;
//				}
            } catch (InterruptedException e) {
            }
            cardcode = ReadCard.getReadCardNum();
            if (!StringUtils.isEmpty(cardcode)) {
                isexit = true;
                Bundle bd = new Bundle();
                bd.putString(SYSConstant.KEYCARDCODE, cardcode);
                Message msg = new Message();
                msg.setData(bd);
                myhandler.sendMessage(msg);
            }

        }
    }
}
