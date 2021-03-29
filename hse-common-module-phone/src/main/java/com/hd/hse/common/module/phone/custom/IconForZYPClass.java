/**
 * Project Name:hse-common-module-phone
 * File Name:PictureForZYPClass.java
 * Package Name:com.hd.hse.common.component.custom
 * Date:2014年12月25日
 * Copyright (c) 2014, xuxinwen@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.custom;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;

/**
 * ClassName:PictureForZYPClass ().<br/>
 * Date:     2014年12月25日  <br/>
 *
 * @author xuxinwen
 * @see
 */
public class IconForZYPClass {

    public static int getIconIDByZYPState(String state) throws HDException {
        if (IWorkOrderStatus.APPR.equals(state)) {
            // 作业中
            return R.drawable.hd_hse_common_module_zyp_state_appr;
        }
        if (IWorkOrderStatus.CAN.equals(state)) {
            // 取消作业
            return R.drawable.hd_hse_common_module_zyp_state_cancle;
        }
        if (IWorkOrderStatus.CLOSE.equals(state)) {
            // 关闭
            return R.drawable.hd_hse_common_module_zyp_state_close;
        }
        if (IWorkOrderStatus.NULLIFY.equals(state)) {
            // 作废
            return R.drawable.hd_hse_common_module_zyp_state_nullify;
        }
        if (IWorkOrderStatus.INPRG.equals(state)) {
            // 审核
            return R.drawable.hd_hse_common_module_zyp_state_check;
        }
        if (IWorkOrderStatus.WAPPR.equals(state)) {
            // 草稿
            return R.drawable.hd_hse_common_module_zyp_state_wappr;
        }
        if (IWorkOrderStatus.CQCLOSE.equals(state)) {
            // 超期关闭
            return R.drawable.hd_hse_common_module_zyp_state_close;
        }
        if (IWorkOrderStatus.APPAUDITED.equals(state)) {
            // 审核
            return R.drawable.hd_hse_common_module_zyp_state_check;
        }
        if (IWorkOrderStatus.REMOTE.equals(state)) {
            return R.drawable.hd_hse_common_module_zyp_state_remote;
        }
        throw new HDException("IconForZYPClass-->" + ": 没有这这个状态！！！-->" + state);
    }

    /**
     * 根据作业票类型获得相应的图标id
     * getDrawableByZYPClass:(). <br/>
     * date: 2014年12月25日 <br/>
     *
     * @param zypClass
     * @return
     * @author xuxinwen
     */
    public static int getIconIDByZYPClass(String zypClass) {

        if (IWorkOrderZypClass.ZYPCLASS_DHZY.equals(zypClass)) {
            // 动火作业
            return R.drawable.hd_hse_common_module_zyp_type_donghuo_selector;
        }
        if (IWorkOrderZypClass.ZYPCLASS_DZZY.equals(zypClass)) {
            // 吊装作业
            return R.drawable.hd_hse_common_module_zyp_type_diaozhuang_selector;
        }

        if (IWorkOrderZypClass.ZYPCLASS_GCZY.equals(zypClass)) {
            //高处
            return R.drawable.hd_hse_common_module_zyp_type_gaochu_selector;
        }

        if (IWorkOrderZypClass.ZYPCLASS_LSYDZY.equals(zypClass)) {
            //临时用电
            return R.drawable.hd_hse_common_module_zyp_type_linshiyongdian_selector;
        }
        if (IWorkOrderZypClass.ZYPCLASS_SXKJ.equals(zypClass)) {
            //受限空间
            return R.drawable.hd_hse_common_module_zyp_type_shouxiankongjian_selector;
        }

        if (IWorkOrderZypClass.ZYPCLASS_SXKJ02.equals(zypClass)) {
            //受限空间02
            return R.drawable.hd_hse_common_module_zyp_type_shouxiankongjian_selector;
        }

        if (IWorkOrderZypClass.ZYPCLASS_WJZY.equals(zypClass)) {
            //挖掘
            return R.drawable.hd_hse_common_module_zyp_type_wajue_selector;
        }
        if (IWorkOrderZypClass.ZYPCLASS_ZYDP.equals(zypClass)) {
            //大票
            return R.drawable.hd_hse_common_module_zyp_type_zuoyexuke_selector;
        }
        if (IWorkOrderZypClass.ZYPCLASS_GXASSET.equals(zypClass)) {
            //管线
            return R.drawable.hd_hse_common_module_zyp_type_mangban_selector;
        }
        // 进车票 add by longgang 2015-6-11
        if (IWorkOrderZypClass.ZYPCLASS_JCP.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_jcp_selector;
        }

        //放射作业票 add by zhaofeng 2015-07-30
        if (IWorkOrderZypClass.ZYPCLASS_SXZY_TJSH.equals(zypClass) || IWorkOrderZypClass.ZYPCLASS_SXZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_fangshe_selector;
        }


        // 涉硫作业 add by liuyang 2015-12-23
        if (IWorkOrderZypClass.ZYPCLASS_SLZZ.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_slzz_selector;
        }
        //普通作业 add by yangning 2017-9-20
        if (IWorkOrderZypClass.ZYPCLASS_PTZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_putong_selector;
        }
        // 带压堵漏作业 add by yangning 2017-10-31
        if (IWorkOrderZypClass.ZYPCLASS_DYDL.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_daiyadulou_selector;
        }
        // 断路作业 add by yangning 2017-10-31
        if (IWorkOrderZypClass.ZYPCLASS_DLZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_duanluzuoye_selector;
        }

        // 盲板抽堵
        if (IWorkOrderZypClass.ZYPCLASS_MBCD.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_mangbanzuoye_selector;
        }

        throw new IllegalArgumentException("IconForZYPClass-->" + ": 没有这个作业类型！！！-->" + zypClass);
        //hd_hse_common_module_zyp_type_fangshe_selector
    }

    /**
     * 根据作业票类型获得相应的Disabled的图标id
     * getDrawableByZYPClass:(). <br/>
     * date: 2014年12月25日 <br/>
     *
     * @param zypClass
     * @return
     * @author xuxinwen
     */
    public static int getDisabledZYPIconIDByZYPClass(String zypClass) {

        if (IWorkOrderZypClass.ZYPCLASS_DHZY.equals(zypClass)) {
            // 动火作业
            return R.drawable.hd_hse_common_module_zyp_type_donghuo_disable;
        }
        if (IWorkOrderZypClass.ZYPCLASS_DZZY.equals(zypClass)) {
            // 吊装作业
            return R.drawable.hd_hse_common_module_zyp_type_diaozhuang_disable;
        }

        if (IWorkOrderZypClass.ZYPCLASS_GCZY.equals(zypClass)) {
            //高处
            return R.drawable.hd_hse_common_module_zyp_type_gaochu_disable;
        }

        if (IWorkOrderZypClass.ZYPCLASS_LSYDZY.equals(zypClass)) {
            //临时用电
            return R.drawable.hd_hse_common_module_zyp_type_linshiyongdian_disable;
        }
        if (IWorkOrderZypClass.ZYPCLASS_SXKJ.equals(zypClass)) {
            //受限空间
            return R.drawable.hd_hse_common_module_zyp_type_shouxiankongjian_disable;
        }
        if (IWorkOrderZypClass.ZYPCLASS_SXKJ02.equals(zypClass)) {
            //受限空间02
            return R.drawable.hd_hse_common_module_zyp_type_shouxiankongjian_disable;
        }

        if (IWorkOrderZypClass.ZYPCLASS_WJZY.equals(zypClass)) {
            //挖掘
            return R.drawable.hd_hse_common_module_zyp_type_wajue_disable;
        }
        if (IWorkOrderZypClass.ZYPCLASS_ZYDP.equals(zypClass)) {
            //大票
            return R.drawable.hd_hse_common_module_zyp_type_zuoyexuke_disable;
        }
        if (IWorkOrderZypClass.ZYPCLASS_GXASSET.equals(zypClass)) {
            //管线
            return R.drawable.hd_hse_common_module_zyp_type_mangban_disable;
        }
        // 进车票 add by longgang 2015-6-11
        if (IWorkOrderZypClass.ZYPCLASS_JCP.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_jcp_disable;
        }
        // 放射作业票 add by zhaofeng 2015-07-30
        if (IWorkOrderZypClass.ZYPCLASS_SXZY_TJSH.equals(zypClass)
                || IWorkOrderZypClass.ZYPCLASS_SXZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_fangshe_disable;
        }
        // 涉硫作业 add by liuyang 2015-12-23
        if (IWorkOrderZypClass.ZYPCLASS_SLZZ.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_slzz_disable;
        }
        //普通作业 add by yangning 2017-9-20
        if (IWorkOrderZypClass.ZYPCLASS_PTZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_putong_disable;
        }
        // 带压堵漏作业 add by yangning 2017-10-31
        if (IWorkOrderZypClass.ZYPCLASS_DYDL.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_daiyadulou_disable;
        }
        // 断路作业 add by yangning 2017-10-31
        if (IWorkOrderZypClass.ZYPCLASS_DLZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_duanluzuoye_disable;
        }

        // 盲板抽堵
        if (IWorkOrderZypClass.ZYPCLASS_MBCD.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_mangbanzuoye_disable;
        }

        throw new IllegalArgumentException("IconForZYPClass-->"
                + ": 没有这个作业类型！！！-->" + zypClass);

    }


    public static int getIsaccordZYPIconIDByZYPClass(String zypClass) {

        if (IWorkOrderZypClass.ZYPCLASS_DHZY.equals(zypClass)) {
            // 动火作业
            return R.drawable.hd_hse_common_module_zyp_type_donghuo_isaccord;
        }
        if (IWorkOrderZypClass.ZYPCLASS_DZZY.equals(zypClass)) {
            // 吊装作业
            return R.drawable.hd_hse_common_module_zyp_type_diaozhuang_isaccord;
        }

        if (IWorkOrderZypClass.ZYPCLASS_GCZY.equals(zypClass)) {
            //高处
            return R.drawable.hd_hse_common_module_zyp_type_gaochu_isaccord;
        }

        if (IWorkOrderZypClass.ZYPCLASS_LSYDZY.equals(zypClass)) {
            //临时用电
            return R.drawable.hd_hse_common_module_zyp_type_linshiyongdian_isaccord;
        }
        if (IWorkOrderZypClass.ZYPCLASS_SXKJ.equals(zypClass)) {
            //受限空间
            return R.drawable.hd_hse_common_module_zyp_type_shouxiankongjian_isaccord;
        }
        if (IWorkOrderZypClass.ZYPCLASS_SXKJ02.equals(zypClass)) {
            //受限空间02
            return R.drawable.hd_hse_common_module_zyp_type_shouxiankongjian_isaccord;
        }

        if (IWorkOrderZypClass.ZYPCLASS_WJZY.equals(zypClass)) {
            //挖掘
            return R.drawable.hd_hse_common_module_zyp_type_wajue_isaccord;
        }
        if (IWorkOrderZypClass.ZYPCLASS_ZYDP.equals(zypClass)) {
            //大票
            return R.drawable.hd_hse_common_module_zyp_type_zuoyexuke_isaccord;
        }
        if (IWorkOrderZypClass.ZYPCLASS_GXASSET.equals(zypClass)) {
            //管线
            return R.drawable.hd_hse_common_module_zyp_type_mangban_isaccord;
        }
        // 进车票 add by longgang 2015-6-11
        if (IWorkOrderZypClass.ZYPCLASS_JCP.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_jcp_isaccord;
        }
        // 涉硫作业 add by liuyang 2015-12-23
        if (IWorkOrderZypClass.ZYPCLASS_SLZZ.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_slzz_isaccord;
        }
        //普通作业 add by yangning 2017-9-20
        if (IWorkOrderZypClass.ZYPCLASS_PTZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_putong_isaccord;
        }
        // 带压堵漏作业 add by yangning 2017-10-31
        if (IWorkOrderZypClass.ZYPCLASS_DYDL.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_daiyadulou_isaccord;
        }
        // 断路作业 add by yangning 2017-10-31
        if (IWorkOrderZypClass.ZYPCLASS_DLZY.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_duanluzuoye_isaccord;
        }

        // 盲板抽堵
        if (IWorkOrderZypClass.ZYPCLASS_MBCD.equals(zypClass)) {
            return R.drawable.hd_hse_common_module_zyp_type_mangbanzuoye_isaccord;
        }

        throw new IllegalArgumentException("IconForZYPClass-->"
                + ": 没有这个作业类型！！！-->" + zypClass);

    }

}

