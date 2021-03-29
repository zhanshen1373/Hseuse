package com.hd.hse.utils;

import java.util.ArrayList;
import java.util.List;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.entity.workorder.WorkTask;

public class WorkOrderUtilTools {

	public WorkOrderUtilTools() {

	}

	/**
	 * compareWorkOrder:(比较本地WorkOrder和远程WorkOrder). <br/>
	 * date: 2015年3月4日 <br/>
	 * 
	 * @author lxf
	 * @param serverWorkTasks
	 * @param localWorkTasks
	 * @return
	 */
	public static List<SuperEntity> compareWorkOrder(
			List<SuperEntity> serverWorkTasks, List<SuperEntity> localWorkTasks) {
		defaultListFalse(serverWorkTasks);
		// 遍历本地workTask
		for (int i = 0; i < localWorkTasks.size(); i++) {
			WorkTask localWorkTask = (WorkTask) localWorkTasks.get(i);
			// 主键
			String localWorkTaskId = localWorkTask.getUd_zyxk_worktaskid();
			// 遍历serverWorkTask,找已下载WorkTask
			for (int j = 0; j < serverWorkTasks.size(); j++) {
				// 本地、server有相同的WorkTask
				if (localWorkTaskId.equals(((WorkTask) serverWorkTasks.get(j))
						.getUd_zyxk_worktaskid())) {

					// 取出服务端一个任务下所有作业票
					List<SuperEntity> serverEntities = extractWorkOrder((WorkTask) serverWorkTasks
							.get(j));
					// 取出本地端一个任务下所有作业票
					List<SuperEntity> localEntities = extractWorkOrder((WorkTask) localWorkTasks
							.get(i));
					// 比较并标记
					realCompareAndMark(serverEntities, localEntities);
				}
			}
		}
		return serverWorkTasks;
	}

	public static void defaultListFalse(List<SuperEntity> serverWorkTasks) {
		if(serverWorkTasks==null){
			return;
		}
		for (int j = 0; j < serverWorkTasks.size(); j++) {
			// 取出服务端一个任务下所有作业票
			List<SuperEntity> serverEntities = extractWorkOrder((WorkTask) serverWorkTasks
					.get(j));
			// 给服务器的打标记
			for (SuperEntity serverE : serverEntities) {
				((WorkOrder) serverE).setDownloaded(false);
			}
		}
	}

	/**
	 * mergeWorkOrder:(合并去掉重复的). <br/>
	 * date: 2015年3月4日 <br/>
	 * 
	 * @author lxf
	 * @param serverWorkTasks
	 * @param localWorkTasks
	 * @return
	 */
	public List<SuperEntity> mergeWorkOrder(List<SuperEntity> serverWorkTasks,
			List<SuperEntity> localWorkTasks) {
		List<SuperEntity> listSum = new ArrayList<SuperEntity>();

		return listSum;
	}

	/**
	 * mergeAndCompareWorkOrder:(合并打标记去掉重复的数据). <br/>
	 * date: 2015年3月4日 <br/>
	 * 
	 * @author lxf
	 * @param serverWorkTasks
	 * @param localWorkTasks
	 * @return
	 */
	public static List<SuperEntity> mergeAndCompareWorkOrder(
			List<SuperEntity> serverWorkTasks, List<SuperEntity> localWorkTasks) {
		List<SuperEntity> listSum = new ArrayList<SuperEntity>();
		// 遍历本地workTask
		for (SuperEntity server : serverWorkTasks) {
			for (SuperEntity local : localWorkTasks) {

				if (((WorkTask) server).getUd_zyxk_worktaskid()
						.equalsIgnoreCase(
								((WorkTask) local).getUd_zyxk_worktaskid())) {
					// 取出服务端一个任务下所有作业票
					List<SuperEntity> serverEntities = extractWorkOrder((WorkTask) server);
					// 取出本地端一个任务下所有作业票
					List<SuperEntity> localEntities = extractWorkOrder((WorkTask) local);
					// 去掉重复的数据
					deleteRepeatWorkTaskInfo(serverEntities, localEntities);
					localWorkTasks.remove(local);
				}
			}

		}
		listSum.addAll(serverWorkTasks);
		listSum.addAll(localWorkTasks);
		return listSum;
	}

	/**
	 * deleteRepeatWorkTaskInfo:(删除重复的数据). <br/>
	 * date: 2015年3月4日 <br/>
	 * 
	 * @author lxf
	 * @param serverEntities
	 * @param localEntities
	 */
	private static void deleteRepeatWorkTaskInfo(
			List<SuperEntity> serverEntities, List<SuperEntity> localEntities) {
		if (localEntities == null || localEntities.size() == 0) {
			return;
		}
		for (SuperEntity localE : localEntities) {
			((WorkOrder) localE).setDownloaded(true);
		}
		// 给服务器的打标记
		for (SuperEntity serverE : serverEntities) {
			for (SuperEntity localE : localEntities) {
				if (((WorkOrder) serverE).getUd_zyxk_zysqid().equalsIgnoreCase(
						((WorkOrder) localE).getUd_zyxk_zysqid())) {
					((WorkOrder) serverE).setDownloaded(true);
					localEntities.remove(localE);
					break;
				}

			}
		}
		// clone
		UtilTools.cloneList(localEntities, serverEntities);
		localEntities.clear();
	}

	/**
	 * 比较作业票
	 * 
	 */
	private static void realCompareAndMark(List<SuperEntity> serverEntities,
			List<SuperEntity> localEntities) {

		if (localEntities == null || localEntities.size() == 0) {
			return;
		}
		// 给服务器的打标记
		for (SuperEntity serverE : serverEntities) {
			for (SuperEntity localE : localEntities) {
				if (((WorkOrder) serverE).getUd_zyxk_zysqid().equalsIgnoreCase(
						((WorkOrder) localE).getUd_zyxk_zysqid())) {
					//((WorkOrder) serverE).setStatus(((WorkOrder) localE).getStatus());
					((WorkOrder) serverE).setDownloaded(true);
					break;
				}
			}

		}
	}

	// 从WorkTask中抽取WorkOrder
	private static List<SuperEntity> extractWorkOrder(WorkTask workTask) {
		return workTask.getChild(WorkOrder.class.getName());

	}
}
