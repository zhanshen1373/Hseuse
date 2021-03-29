package com.RS.AndroidSDK;

import android.util.Log;

//.so库文件属于jni动态库，classname和packagename必须用此固定名称。
public class HardwareCtrl
{
	/*注意：
  1.“启动(__init)”模块后才可以“读取(__read)”数据。
  2.不需要读取数据的时候必须“关闭(__close)”模块，防止下次启动冲突也为了节电。
  3.由于每个模块硬件电路的不同，“启动”(__init)模块后可能会有1-5秒后才能读取数据。
  */
  //射频卡////////////tag参数： 1 表示125K卡，2 表示13.56M卡/////////////////////////////////////////////////
	
	/**
	 * Fridinit:(). <br/>
	 * date: 2014年10月8日 <br/>
	 *
	 * @author lxf
	 * @param tag 2读1433 3读15693 5以上两种都支持
	 * @return
	 */
	static public native String Fridinit(int tag);
	static public native String Fridread();
	static public native String Fridclose();
	//温度///////////////////////////////////////////////////////////////
	static public native String IRTinit(String tag);
	static public native String IRTread();
	static public native String IRTclose();
	//振动/////////////tag  d 1, v 0, a 2//freq 0低频 1高频/////////////
	static public native String VIBinit(int tag,int freq);
	static public native String VIBread();
	static public native String VIBclose();
	static public native int SetDZ(int tag,int freq,int dz);
	static public native int GetDZ(int tag,int freq);
  static {
      try {
      	System.loadLibrary("RSdevice35");
      } catch (UnsatisfiedLinkError e) {
          Log.d("HardwareControler", "RS. library not found!");
      }
  }
}