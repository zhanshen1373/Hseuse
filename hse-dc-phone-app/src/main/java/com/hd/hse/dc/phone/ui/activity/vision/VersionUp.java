package com.hd.hse.dc.phone.ui.activity.vision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.custom.MessageProgressDialog;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.listener.common.GetVersionInfo;
import com.hd.hse.dc.business.listener.common.VersionUpgrade;
import com.hd.hse.dc.phone.R;
import com.hd.hse.entity.common.UpdataInfo;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VersionUp {
    private Context context;

    public VersionUp(Context context) {
        this.context = context;
    }

    // /**
    // * initData:(数据初始化). <br/>
    // * date: 2014年11月4日 <br/>
    // *
    // * @author ZhangJie
    // */
    // private void initData() {
    // // 提示是否初始化数据
    // MessageDialog.Builder builder = new MessageDialog.Builder(context);
    // builder.setTitle("提示");
    // builder.setMessage("是否确定初始化?");
    // builder.setPositiveButton("", new DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // showDialog("初始化", "收集要下载的信息，请稍后...");
    // BasicDataInit basicinit = new BasicDataInit();
    // BusinessAction action = new BusinessAction(basicinit);
    // // 后台业务处理
    // runProThread(action);
    // dialog.dismiss();
    // }
    // });
    // builder.setNegativeButton("",
    // new android.content.DialogInterface.OnClickListener() {
    // public void onClick(DialogInterface dialog, int which) {
    // dialog.dismiss();
    // }
    // });
    // builder.createWarm().show();
    // }

    /**
     * installApk:(自动安装APK). <br/>
     * date: 2014年9月9日 <br/>
     *
     * @param filepath
     * @author lxf
     */
    protected void installApk(String filepath) {
        File file = new File(filepath);
        /*Intent intent = new Intent();
        // 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
		context.startActivity(intent);*/


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, "${applicationId}.fileprovider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);


    }

    /**
     * getVersionName:(获取系统当前版本). <br/>
     * date: 2014年9月9日 <br/>
     *
     * @return
     * @throws NameNotFoundException
     * @author lxf
     */
    private String getVersionName() throws NameNotFoundException {
        PackageManager packageManger = context.getPackageManager();
        PackageInfo packinfo = packageManger.getPackageInfo(
                context.getPackageName(), 0);
        return packinfo.versionName;
    }

    /**
     * showUpdataDialog:(显示版本升级提示框). <br/>
     * date: 2014年9月9日 <br/>
     *
     * @param oldversion
     * @param newversion
     * @author lxf
     */
    protected void showUpdataDialog(final String oldversion,
                                    final String newversion, final String downUrl) {

        MessageDialog.Builder builder = new MessageDialog.Builder(context);
        builder.setTitle("版本升级");

        String[] versionList = context.getResources().getStringArray(R.array.hd_hse_version_list);
        boolean needUpdate = true;
        for (String v : versionList) {
            if (v.equals(newversion)) {
                needUpdate = false;
                break;
            }
        }

//		int oldv = 100, newv = 100;
        if (!oldversion.equalsIgnoreCase(newversion)) {
            // 取得当前版本
//			oldv = resloveVersionFormat(oldversion);
//			newv = resloveVersionFormat(newversion.trim());
            if (methodIsHaveNewVersion) {
                sendHaveNewVersionBroadcastReceiver(true);
            }
            builder.setMessage("当前版本:" + oldversion + " |最新版本:" + newversion);

        } else {
            if (methodIsHaveNewVersion) {
                // 表示后天调用----发送广播
                sendHaveNewVersionBroadcastReceiver(false);
            }
            // 表示点击按钮过来
            builder.setMessage("当前已是最新版本:" + newversion);

        }
        if (needUpdate) {
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!oldversion.equalsIgnoreCase(newversion)) {
                                SystemProperty.getSystemProperty().setClickCancel(false);
                                // 最好显示进度条
                                showDialog("版本升级", "下载最新版本，请稍后...");
                                VersionUpgrade basicupdate = new VersionUpgrade(context);
                                BusinessAction action = new BusinessAction(
                                        basicupdate);
                                runProThreadVersion(action, downUrl,
                                        IActionType.WEB_VUPGRADE);
                            }
                            dialog.dismiss();
                            // 设置你的操作事项
                        }
                    });
        }
        if (!oldversion.equalsIgnoreCase(newversion) || !needUpdate) {
            // 当点取消按钮时进行登录
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        if (methodIsHaveNewVersion) {
            if (!oldversion.equalsIgnoreCase(newversion)) {
                // 表示有新版本
                builder.createWarm().show();
            }
        } else {
            builder.createWarm().show();
        }

    }

    /**
     * sendHaveNewVersionBroadcastReceiver:(发送广播). <br/>
     * date: 2015年8月17日 <br/>
     *
     * @param isnew
     * @author lxf
     */
    private void sendHaveNewVersionBroadcastReceiver(boolean isnew) {
        Intent intent = new Intent();
        intent.setAction("ui.activity.main.MainActivity.BroadcastReceiver");
        intent.putExtra("ishavenew", isnew);
        context.sendBroadcast(intent);
    }

    /**
     * getVersionUpgradeInfo:(获取版本信息并升级). <br/>
     * date: 2014年9月9日 <br/>
     *
     * @author lxf
     */
    public void getVersionUpgradeInfo() {
        methodIsHaveNewVersion = false;
        GetVersionInfo getVersioninfo = new GetVersionInfo(context);
        BusinessAction action = new BusinessAction(getVersioninfo);
        runProThreadVersion(action, null, IActionType.WEB_GETVERSIONUPGRADE);

    }

    /**
     * methodIsHaveNewVersion:TODO(方法来源).
     */
    private boolean methodIsHaveNewVersion = false;

    /**
     * isHaveNewVersion:(判断是否有新版本). <br/>
     * date: 2015年8月17日 <br/>
     *
     * @author lxf
     */
    public void isHaveNewVersion(String versionID) {
        methodIsHaveNewVersion = true;
        GetVersionInfo getVersioninfo = new GetVersionInfo(context);
        getVersioninfo.setKeyValueVersion(versionID);
        BusinessAction action = new BusinessAction(getVersioninfo);
        runProThreadVersion(action, null, IActionType.WEB_GETVERSIONUPGRADE);
    }

    /**
     * getVersionUpgradeInfo:(获取版本信息并升级). <br/>
     * date: 2014年9月9日 <br/>
     *
     * @author lxf
     */
    public void getVersionUpgradeInfo(String versionID) {
        methodIsHaveNewVersion = false;
        GetVersionInfo getVersioninfo = new GetVersionInfo(context);
        getVersioninfo.setKeyValueVersion(versionID);
        BusinessAction action = new BusinessAction(getVersioninfo);
        runProThreadVersion(action, null, IActionType.WEB_GETVERSIONUPGRADE);

    }

    boolean isInstall = true;

    /**
     * 基础数据更新
     */
    private void runProThread(BusinessAction action) {
        runProThreadVersion(action, null, "");
        // runProThread(action, null);
    }

    /**
     * runProThreadVersion:(版本升级的进程). <br/>
     * date: 2014年9月9日 <br/>
     *
     * @param action
     * @param listEntity
     * @param actionType
     * @author lxf
     */
    private void runProThreadVersion(BusinessAction action, Object listEntity,
                                     final String actionType) {
        BusinessAsyncTask task = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {
                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        int process = msg.getCurrent();
                        if (null != mDialog) {
                            mDialog.setMessage(msg.getMessage());
                            if (process >= 100) {
                                // mDialog.dismiss();
                                // mDialog=null;
                            } else {
                                mDialog.setProgress(process);
                            }
                        }
                    }

                    @Override
                    public void error(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        if (null != mDialog) {
                            mDialog.setMessage(msg.getMessage());
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                            }
                            // 设置显示按钮 提示错误
                            mDialog.executFailed();
                        } else {
                            ToastUtils.toast(context, msg.getMessage());
                        }
                    }

                    @Override
                    public void end(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        if (actionType
                                .equalsIgnoreCase(IActionType.WEB_GETVERSIONUPGRADE)) {
                            versionUpgrade(msg.getReturnResult());
                        }
                        if (msg.getMessage().contains("apk")) {
                            if (msg.getCurrent() == 100 && isInstall) {
                                isInstall = false;
                                if (null != mDialog)
                                    mDialog.dismiss();
                                if (!SystemProperty.getSystemProperty().isClickCancel()) {
                                    installApk(msg.getMessage());
                                }
                                isInstall = true;
                            }
                        } else {
                            if (null != mDialog) {
                                mDialog.setMessage("恭喜你,操作成功!");
                                mDialog.setProgress(100);
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                }
                                mDialog.executSuccessful();
                            } else {
                                if (!StringUtils.isEmpty(msg.getMessage())) {
                                    ToastUtils.toast(context, msg.getMessage());
                                }
                            }
                        }

                    }
                });
        try {
            task.execute("", listEntity);
        } catch (HDException e) {
            ToastUtils.imgToast(context, R.drawable.hd_hse_common_msg_wrong,
                    "系统版本升级失败，请联系系统管理员！");
        }
    }

    /**
     * versionUpgrade:(版本升级). <br/>
     * date: 2014年9月9日 <br/>
     *
     * @param obj
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    private void versionUpgrade(Object obj) {
        HashMap<String, String> hasmap = null;
        UpdataInfo info = new UpdataInfo();
        try {
            if (obj instanceof Map) {
                hasmap = (HashMap<String, String>) obj;
                if (hasmap.containsKey(PadInterfaceRequest.KEYPADAPKURL))
                    info.setUrl(hasmap.get(PadInterfaceRequest.KEYPADAPKURL));
                if (hasmap.containsKey(PadInterfaceRequest.KEYPADVERSION))
                    info.setVersion(hasmap
                            .get(PadInterfaceRequest.KEYPADVERSION));
            } else {
                throw null;
            }
            if (StringUtils.isEmpty(info.getUrl())) {
                throw new HDException("请联系管理，在PC端配置升级地址.");
            }
            if (StringUtils.isEmpty(info.getVersion())) {
                throw new HDException("请联系管理，在PC端配置版本信息.");
            }
            // 获取当前版本
            String versionname = getVersionName();
            // 获取服务器版本和下载地址apk地址
            //showUpdataDialog(versionname, "1.0.3", info.getUrl());
            showUpdataDialog(versionname, info.getVersion(), info.getUrl());
        } catch (Exception e) {
            ToastUtils.toast(context, "升级出错，请联系系统管理员！");
        }

    }

    MessageProgressDialog mDialog = null;

    private void showDialog(String titile, String message) {
        mDialog = new MessageProgressDialog(context);
        mDialog.setTitle(titile);
        mDialog.setMessage(message);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // mDialog.executSuccessful();
        mDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                // cancel(true);

            }
        });
        mDialog.show();
        mDialog.setMax(100);
    }

    /**
     * resloveVersionFormat:(解析版本格式). <br/>
     * date: 2014年9月19日 <br/>
     *
     * @param code
     * @return
     * @author lxf
     */
    private int resloveVersionFormat(String code) {
        int intcode = Integer.parseInt(code.replace(".", ""));
        return intcode;
    }

}
