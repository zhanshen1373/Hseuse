/**
 * Project Name:hse-entity-service
 * File Name:IQueryWorkInfo.java
 * Package Name:com.hd.hse.service.workorder.queryinfo
 * Date:2014年10月16日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.service.workorder.queryinfo;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.base.Department;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.EnergyIsolation;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.base.HazardNotify;
import com.hd.hse.entity.base.MultitermMeasureAffirm;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.PriorityLine;
import com.hd.hse.entity.base.ZyxxTableName;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.entity.common.SysActionAgeConfig;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.workorder.AppAnlsCfg;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkGuardEquipment;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: IQueryWorkInfo ()<br/>
 * date: 2014年10月16日 <br/>
 *
 * @author zhaofeng
 */
public interface IQueryWorkInfo extends IQuery {

    /**
     * 接口说明： 方法中的异步回调函数如果设置为null，则走本地查询； 要走异步需要实现异步回调函数；
     *
     */

    /**
     * queryWorkInfo:(作业票信息). <br/>
     * date: 2014年10月16日 <br/>
     *
     * @param workEntity
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public WorkOrder queryWorkInfo(SuperEntity workEntity,
                                   IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryWorkInfoConfigInfo:(). <br/>
     * date: 2014年10月17日 <br/>
     *
     * @param workEntity
     * @param functionType      功能点（审批，复查，延期，关闭/取消）
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<PDAWorkOrderInfoConfig> queryWorkInfoConfigInfo(
            SuperEntity workEntity, String functionType,
            IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryWorkInfoConfigInfo:(). <br/>
     * date: 2014年11月4日 <br/>
     *
     * @param workEntity
     * @param functionType      具体功能
     * @param contye            具体类别的
     * @param callEventListener
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<PDAWorkOrderInfoConfig> queryWorkInfoConfigInfo(
            SuperEntity workEntity, String functionType, Integer contye,
            IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryHarmInfo:(危害信息). <br/>
     * date: 2014年10月16日 <br/>
     *
     * @param workEntity
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<HazardNotify> queryHarmInfo(SuperEntity workEntity,
                                            PDAWorkOrderInfoConfig pdaConfig,
                                            IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryMeasureInfo:(措施信息). <br/>
     * date: 2014年10月16日 <br/>
     *
     * @param workEntity
     * @param workConfigEntity  tab配置信息
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<WorkApplyMeasure> queryMeasureInfo(SuperEntity workEntity,
                                                   SuperEntity workConfigEntity,
                                                   IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryPpeInfo:(个人防护装备). <br/>
     * date: 2014年10月16日 <br/>
     *
     * @param workEntity
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<WorkGuardEquipment> queryPpeInfo(SuperEntity workEntity,
                                                 IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryGasInfo:(气体检测). <br/>
     * 新增气体查询 date: 2014年10月16日 <br/>
     *
     * @param workEntity
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public GasDetection queryGasInfo(SuperEntity workEntity,
                                     IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryHistoryGasInfo:(获取该作业票下的所有气体检测记录). <br/>
     * 历史数据查询 date: 2014年10月20日 <br/>
     *
     * @param workEntity
     * @param callEventListener
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public ArrayList<Map<String, String>> queryHistoryGasInfo(
            SuperEntity workEntity, IQueryCallEventListener callEventListener)
            throws HDException;

    /**
     * queryUndoneGasInfo:(获取最近一次未完成的气体检测记录). <br/>
     * date: 2014年10月23日 <br/>
     * 判断是否有未操作完成，如果没有 返回 null
     *
     * @param workEntity
     * @param callEventListener
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public GasDetection queryUndoneGasInfo(SuperEntity workEntity,
                                           IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryReviewInfo:(措施复查). <br/>
     * date: 2014年10月16日 <br/>
     *
     * @param workEntity
     * @param workConfigEntity  tab配置信息
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public WorkMeasureReview queryReviewInfo(SuperEntity workEntity,
                                             SuperEntity workConfigEntity,
                                             IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryDelayInfo:(延期信息). <br/>
     * date: 2014年10月16日 <br/>
     *
     * @param workEntity
     * @param zyscNum           复查编号
     * @param callEventListener 异步回调函数
     * @return 返回null时。作业延期结束时间取作业票的实际结束时间
     * @throws HDException
     * @author zhaofeng
     */
    public WorkDelay queryDelayInfo(SuperEntity workEntity, String zyscNum,
                                    IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryApprovalPermission:(审批环节点查询). <br/>
     * date: 2014年10月17日 <br/>
     *
     * @param workEntity
     * @param workConfigEntity  tab配置信息
     * @param personRecord      人员记录信息 -针对复查，延期和气体检测这样的重复操作
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<WorkApprovalPermission> queryApprovalPermission(
            SuperEntity workEntity, SuperEntity workConfigEntity,
            SuperEntity record, IQueryCallEventListener callEventListener)
            throws HDException;

    /**
     * querySiteAuditBasicInfo:(查询作业现场核查基础信息《组合信息》). <br/>
     * date: 2014年10月20日 <br/>
     *
     * @param workEntity
     * @param functionType      功能点（审批，复查，延期，关闭/取消）
     * @param callEventListener
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public WorkOrder querySiteAuditBasicInfo(SuperEntity workEntity,
                                             String functionType, IQueryCallEventListener callEventListener)
            throws HDException;

    /**
     * queryPhoto:(根据来源数据、功能模块查询照片). <br/>
     * date: 2014年10月23日 <br/>
     *
     * @param srcEntity
     * @param configEntity
     * @return
     * @throws HDException
     * @author lg
     */
    public List<Image> queryPhoto(SuperEntity srcEntity,
                                  SuperEntity configEntity) throws HDException;

    /**
     * queryPhoto:(根据来源数据、功能模块查询照片--合并会签). <br/>
     * date: 2016年7月12日 <br/>
     *
     * @param srcEntitys
     * @param configEntity
     * @return
     * @throws HDException
     * @author liuyang
     */
    public List<Image> queryPhoto(List<WorkOrder> srcEntitys,
                                  SuperEntity configEntity) throws HDException;

    /**
     * querySysActionAgeConfig:(获取动作时效配置信息). <br/>
     * date: 2014年11月5日 <br/>
     *
     * @param action
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public SysActionAgeConfig querySysActionAgeConfig(String action)
            throws HDException;

    /**
     * queryReportFormUrl:(获取浏览报表的url). <br/>
     * date: 2014年11月6日 <br/>
     *
     * @param workEntity 作业许可实体
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public String queryReportFormUrl(SuperEntity workEntity) throws HDException;

    /**
     * queryItemByItemInfo:(获取逐条记录表的信息). <br/>
     * date: 2014年12月5日 <br/>
     *
     * @param multitermMeasureAffirm
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<MultitermMeasureAffirm> queryItemByItemInfo(
            MultitermMeasureAffirm multitermMeasureAffirm,
            IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryVirtualCards:(获取虚拟位置集合). <br/>
     * date: 2015年3月27日 <br/>
     *
     * @param callEventListener
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<PositionCard> queryVirtualCards(
            IQueryCallEventListener callEventListener) throws HDException;

    // //////////////////////////////

    /**
     * querySiteAuditBasicInfo:(查询会签页签《组合信息》). <br/>
     * date: 2014年08月27日 <br/>
     *
     * @param workEntity
     * @param functionType      功能点（审批，复查，延期，关闭/取消）
     * @param callEventListener
     * @return
     * @throws HDException
     * @author lxf
     */
    public WorkOrder querySiteAuditSignInfo(SuperEntity workEntity,
                                            String functionType, IQueryCallEventListener callEventListener)
            throws HDException;

    /**
     * queryApprovalPermission:(查询合并环节点). <br/>
     * date: 2014年10月17日 <br/>
     *
     * @param workEntity
     * @param workConfigEntity  tab配置信息
     * @param personRecord      人员记录信息 -针对复查，延期和气体检测这样的重复操作
     * @param callEventListener 异步回调函数
     * @return
     * @throws HDException
     * @author lxf
     */
    public List<WorkApprovalPermission> queryApprovalPermission(
            List<SuperEntity> workEntity, SuperEntity workConfigEntity,
            SuperEntity record, IQueryCallEventListener callEventListener)
            throws HDException;

    /**
     * queryAndMultiplexGasInfo:(气体检测复用). <br/>
     * date: 2015年9月9日 <br/>
     *
     * @param workEntity
     * @param workConfigEntity  tab配置信息
     * @param callEventListener
     * @throws HDException
     * @author lxf
     */
    public GasDetection queryAndMultiplexGasInfo(SuperEntity workEntity,
                                                 SuperEntity workConfigEntity,
                                                 IQueryCallEventListener callEventListener,
                                                 List<WorkApprovalPermission> approvalPermission, String spCode)
            throws HDException;

    /**
     * querySignImgUrls:(获取手签图片). <br/>
     * date: 2015年10月28日 <br/>
     *
     * @param tableIds
     * @param callEventListener
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public List<Image> querySignImgUrls(String tableName, String[] tableIds)
            throws HDException;

    /**
     * queryDomain:(查询动态域). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @throws HDException
     * @author LiuYang
     */
    public List<Domain> queryDomain(String domainId,
                                    IQueryCallEventListener callEventListener) throws HDException;

    public List<Domain> queryDomain(String domainId,String value,
                                    IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryTJUrls:(通过code查询统计分析的URL). <br/>
     * date: 2015年11月25日 <br/>
     *
     * @param code
     * @param callEventListener
     * @return
     * @throws HDException
     * @author LiuYang
     */
    public List<Domain> queryTJUrls(String code,
                                    IQueryCallEventListener callEventListener) throws HDException;

    /**
     * 查询部门列表 仅查询当前部门以下的部门 queryDept:(). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @author LiuYang
     */
    public List<Department> queryDept(String currentDeptNum,
                                      IQueryCallEventListener callEventListener) throws HDException;

    /**
     * 查询部门列表 仅查询当前部门以下的部门 queryDept:(). <br/>
     * date: 2017年3月9日 <br/>
     *
     * @throws HDException
     * @author yn
     */
    public List<Department> queryDept(String currentDeptNum) throws HDException;

    /**
     * 查询部门 仅查询isQuery = 1 的
     * <p>
     * Created by liuyang on 2016年5月12日
     *
     * @param callEventListener
     * @return
     * @throws HDException
     */
    public List<Department> queryDept(boolean isquery, IQueryCallEventListener callEventListener)
            throws HDException;

    /**
     * 查询传入部门的父级部门 queryParentDept:(). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @author LiuYang
     */
    public Department queryDefaultDept(String currentDeptNum,
                                       IQueryCallEventListener callEventListener) throws HDException;

    /**
     * queryIPAndPort:(查询IP和端口号). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @throws HDException
     * @author LiuYang
     */
    public String queryIPAndPort(IQueryCallEventListener callEventListener)
            throws HDException;

    /**
     * queryAppModule:(根据type查询AppModule). <br/>
     * date: 2015年11月25日 <br/>
     *
     * @param type
     * @param callEventListener
     * @return
     * @throws HDException
     * @author LiuYang
     */
    public List<AppModule> queryAppModule(String type,
                                          IQueryCallEventListener callEventListener) throws HDException;

    public String[] querySignInfoItemByItem(String sqId, String opId)
            throws HDException;

    /**
     * 查询能量隔离单主表和子表数据
     * <p>
     * Created by liuyang on 2016年2月17日
     *
     * @param ud_zyxk_zysqid
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public EnergyIsolation queryEnergyIsolation(String ud_zyxk_zysqid,
                                                IQueryCallEventListener queryCallEventListener) throws HDException;

    /**
     * 查询危害识别tabs
     * <p>
     * Created by liuyang on 2016年3月9日
     *
     * @param workOrder
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public List<PDAWorkOrderInfoConfig> queryHarmTabConfigs(
            WorkOrder workOrder, IQueryCallEventListener queryCallEventListener)
            throws HDException;

    /**
     * 查询用于合并会签拍照的大票信息
     * <p>
     * Created by liuyang on 2016年7月12日
     *
     * @param workOrders
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public List<WorkOrder> queryWorkOrderListForCamera(
            List<WorkOrder> workOrders,
            IQueryCallEventListener queryCallEventListener) throws HDException;

    /**
     * 查询临时用电信息
     * <p>
     * Created by liuyang on 2016年7月20日
     *
     * @param workOrder
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public Map<String, Object> queryTempEleInfo(WorkOrder workOrder,
                                                IQueryCallEventListener queryCallEventListener) throws HDException;

    /**
     * 查询优先级（一票制） Created by liuyang on 2016年9月18日
     *
     * @param workOrder
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public PriorityLine queryPriority(WorkOrder workOrder,
                                      IQueryCallEventListener queryCallEventListener) throws HDException;

    /**
     * 查询统计信息
     *
     * @return
     * @throws HDException
     */
    public List<AppAnlsCfg> queryAppAnlsCfg() throws HDException;

    /**
     * 从hse专业详细定义里查主管科室
     *
     * @return
     * @throws HDException
     */
    public List<ZyxxTableName> queryDeptFromhdrcgl() throws HDException;
    /**
     * 从hse专业详细定义里查所属专业
     *
     * @return
     * @throws HDException
     */
    public List<ZyxxTableName> queryZyFromhdrcgl(String flag) throws HDException;
}
