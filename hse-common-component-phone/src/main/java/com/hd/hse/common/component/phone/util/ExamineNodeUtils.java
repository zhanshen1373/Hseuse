package com.hd.hse.common.component.phone.util;

import java.util.List;

import com.hd.hse.common.entity.SuperEntity;

/**
 * ClassName: ExamineNodeUtils (审批环节点状态计算)<br/>
 * date: 2014年10月17日 <br/>
 * 
 * @author lg
 * @version
 */
public class ExamineNodeUtils{

	/**
	 * sortExamineNode:(根据审核时间、是否多人刷卡、是否必须审核、审核顺序设置环节点状态). <br/>
	 * date: 2014年10月17日 <br/>
	 * 
	 * @author lg
	 * @param lstNode
	 */
	public static void sortExamineNode(List<? extends SuperEntity> lstNode) {
		if (lstNode == null || lstNode.isEmpty()) {
			return;
		}
		// 最终确认人环节点判断，若最终确认人已审核，则所有环节点不可用
		SuperEntity lastNode = lstNode.get(lstNode.size() - 1);
		if ((Integer) lastNode.getAttribute("isend") == 1
				&& lastNode.getAttribute("sptime") != null && !"".equals(lastNode.getAttribute("sptime"))) {
			for (SuperEntity node : lstNode) {
				node.setAttribute("isexmaineable", 0);
			}
		}
		// 一般处理，遍历环节点，设置环节点状态
		else {
			Integer maxpdapaixu = 0;// 可刷卡的最大审核顺序
			// 一次循环，环节点状态初始化
			for (SuperEntity node : lstNode) {
				// 初始状态都为可用
				node.setAttribute("isexmaineable", 1);
				// 已审批，且不为多人刷卡，则不可用
				if (node.getAttribute("sptime") != null && !"".equals(node.getAttribute("sptime"))
						&& (Integer) node.getAttribute("bpermulcard") == 0) {
					node.setAttribute("isexmaineable", 0);
				}
				maxpdapaixu = (Integer) node.getAttribute("pdapaixu");// 默认最大审批顺序
			}
			// 二次循环，计算最大可刷卡的审核顺序
			for (SuperEntity node : lstNode) {
				// (允许多人刷卡+且已审核)或不必须审核不影响刷卡顺序
				if (!(((Integer) node.getAttribute("bpermulcard") == 1 && node
						.getAttribute("sptime") != null && !"".equals(node.getAttribute("sptime"))) || (Integer) node
						.getAttribute("ismust") == 0)) {
					if ((Integer) node.getAttribute("isexmaineable") == 1) {
						maxpdapaixu = (Integer) node
								.getAttribute("pdapaixu");// 审批顺序
						break;
					}
				}
			}
			// 三次循环，更新环节点状态
			for (SuperEntity node : lstNode) {
				if ((Integer) node.getAttribute("pdapaixu") > maxpdapaixu) {
					node.setAttribute("isexmaineable", 0);
				}
			}
		}
	}
}
