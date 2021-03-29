package com.hd.hse.common.module.phone.ui.utils;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.utils.ImageCompress.OnCompressListener;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动拍照，手动拍照图片处理类
 * 
 * @author yn
 * 
 */
public class ImageUtils {
	private static Logger logger = LogUtils
			.getLogger(PaintSignatureActivity.class);
	/**
	 * 临时图片路径
	 */
	private final String TEMPPATH = SystemProperty.getSystemProperty()
			.getRootDBpath() + File.separator + "temp.jpg";
	/**
	 * 存放照片文件夹的文件夹路径,带"/"
	 */
	private final String saveImgPath = SystemProperty.getSystemProperty()
			.getRootDBpath() + File.separator;
	/**
	 * 图片压缩类
	 */
	private ImageCompress mImageCompress;

	/**
	 * 保存图片，先保存到本地，成功后保存到数据库,未合并审批
	 */
	public void saveImage(PDAWorkOrderInfoConfig mPDAWorkOrderInfoConfig,
			WorkApprovalPermission curApproveNode, WorkOrder mWorkOrder,
			Image image) {
		// 当前时间带时分秒
		String createTime = SystemProperty.getSystemProperty()
				.getSysSHDateTime();
		// 文件名规则：功能（措施/会签）+环节点（名称）+时间戳（避免照片名称重复）
		String spfield_desc = curApproveNode.getSpfield_desc();
		String[] spfildstr = spfield_desc.split("#");
		if (spfildstr.length > 0) {
			spfield_desc = spfildstr[spfildstr.length - 1];
		} else {
			spfield_desc = "刷卡人";
		}
		String saveName = mPDAWorkOrderInfoConfig.getSname() + "_"
				+ spfield_desc + "_" + createTime + ".jpg";
		// 保存文件路径
		String savePath = saveImgPath + mWorkOrder.getUd_zyxk_zysqid()
				+ File.separator + saveName;
		// image进行数据设置
		if (image != null) {
			String timeStr = ServerDateManager.getCurrentTime();
			Long time = ServerDateManager.getCurrentTimeMillis();
			image.setTablename("ud_zyxk_zyspryjl");
			if (curApproveNode.getModified_spryjlid() != null
					&& !curApproveNode.getModified_spryjlid().equals("")) {
				image.setTableid(curApproveNode.getModified_spryjlid());
			} else {
				image.setTableid(curApproveNode.getUd_zyxk_zyspryjlid());
			}
			image.setImagepath(savePath);
			image.setImagename(saveName);
			//对多人刷卡进行人名id进行拆分
			String createuser="";
			if (curApproveNode.getPersonid().contains(",")) {
				String createusers[]=curApproveNode.getPersonid().split(",");
				createuser=createusers[createusers.length-1];
			}else {
				createuser=curApproveNode.getPersonid();
			}
			image.setCreateuser(createuser);
			//对多人刷卡进行人名进行拆分
			String createusername="";
			if (curApproveNode.getPersondesc().contains(",")) {
				String createusernames[]=curApproveNode.getPersondesc().split(",");
				createusername=createusernames[createusernames.length-1];
			}else {
				createusername=curApproveNode.getPersondesc();
			}
			image.setCreateusername(createusername);
			image.setCreatedate(timeStr);
			image.setFuncode(mPDAWorkOrderInfoConfig.getPscode());
			image.setContype(mPDAWorkOrderInfoConfig.getContype());
			image.setZysqid(mWorkOrder.getUd_zyxk_zysqid());
			image.setUpdateDate(time);
			// 先保存到本地，成功后保存到数据库
			saveImage(image,null);

		}

	}

	/**
	 * 合并审批
	 */
	public void saveImage(PDAWorkOrderInfoConfig mPDAWorkOrderInfoConfig,
			WorkApprovalPermission curApproveNode, List<WorkOrder> mWorkOrders) {
		//if (mWorkOrders != null) {
			String zyspryjlids = curApproveNode.getModified_spryjlid();
			// 获得tableid
			String[] tableIds = curApproveNode.getModified_spryjlid()
					.split(",");
			// 获得orderIds
			String[] orderIds = curApproveNode.getStrzysqid().split(",");

			// for循环得到savePaths和images
			List<String> savePaths = new ArrayList<String>();
			List<Image> images = new ArrayList<Image>();
			for (int i = 0; i < orderIds.length; i++) {
				// 获得orderId
				String orderId = orderIds[i].substring(1,
						orderIds[i].length() - 1);
				curApproveNode.setModified_spryjlid(tableIds[i]);

				String createTime = SystemProperty.getSystemProperty()
						.getSysSHDateTime();
				// 文件名规则：功能（措施/会签）+环节点（名称）+时间戳（避免照片名称重复）
				String spfield_desc = curApproveNode.getSpfield_desc();
				String[] spfildstr = spfield_desc.split("#");
				if (spfildstr.length > 0) {
					spfield_desc = spfildstr[spfildstr.length - 1];
				} else {
					spfield_desc = "刷卡人";
				}
				String saveName = mPDAWorkOrderInfoConfig.getSname() + "_"
						+ spfield_desc + "_" + createTime + ".jpg";
				// 保存文件路径
				String savePath = saveImgPath + orderId
						+ File.separator + saveName;
				Image image=new Image();
				String timeStr = ServerDateManager.getCurrentTime();
				Long time = ServerDateManager.getCurrentTimeMillis();
				image.setTablename("ud_zyxk_zyspryjl");
				if (curApproveNode.getModified_spryjlid() != null
						&& !curApproveNode.getModified_spryjlid().equals("")) {
					image.setTableid(curApproveNode.getModified_spryjlid());
				} else {
					image.setTableid(curApproveNode.getUd_zyxk_zyspryjlid());
				}
				image.setImagepath(savePath);
				image.setImagename(saveName);
				
				
				//合并审批，对多人刷卡进行人名id进行拆分
				String createuser="";
				if (curApproveNode.getPersonid().contains(",")) {
					String createusers[]=curApproveNode.getPersonid().split(",");
					createuser=createusers[createusers.length-1];
				}else {
					createuser=curApproveNode.getPersonid();
				}
				image.setCreateuser(createuser);
				//合并审批，对多人刷卡进行人名进行拆分
				String createusername="";
				if (curApproveNode.getPersondesc().contains(",")) {
					String createusernames[]=curApproveNode.getPersondesc().split(",");
					createusername=createusernames[createusernames.length-1];
				}else {
					createusername=curApproveNode.getPersondesc();
				}
				image.setCreateusername(createusername);
				/*image.setCreateuser(curApproveNode.getPersonid());
				image.setCreateusername(curApproveNode.getPersondesc());*/
				image.setCreatedate(timeStr);
				image.setFuncode(mPDAWorkOrderInfoConfig.getPscode());
				
				image.setContype(mPDAWorkOrderInfoConfig.getContype());
				image.setZysqid(orderId);
				image.setUpdateDate(time);
				images.add(image);
			}
			curApproveNode.setModified_spryjlid(zyspryjlids);
			//压缩图片
			saveImage(null, images);
		//}
	}

	/**
	 * 保存图片到sd卡，未合并审批
	 */
	private void saveImage(final Image image,final List<Image> images) {
		File tempFile = new File(TEMPPATH);
		// 进行压缩处理
		mImageCompress = new ImageCompress(tempFile);
		mImageCompress.setOnCompressListener(new OnCompressListener() {

			@Override
			public void onSuccess(File file) {
				// 压缩成功后，将图片信息保存到数据库
				if (images==null) {
					if (copyFile(TEMPPATH, image.getImagepath())) {
						saveImageToDB(image);
					}
				}else {
					for (int i = 0; i < images.size(); i++) {
						if (copyFile(TEMPPATH, images.get(i).getImagepath())) {
							saveImageToDB(images.get(i));
						}
					}
				}
				// 删除临时图片
				File tempFile = new File(TEMPPATH);
				if (tempFile.exists()) {
					tempFile.delete();
				}
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onError(String error) {

			}
		});
		mImageCompress.compressImage();
	}

	/**
	 * 保存图片到sqlite，未合并审批
	 */
	private void saveImageToDB(Image img) {

		BusinessAction action = new BusinessAction();
		Image image;
		try {
			image = (Image) action.addEntity(Image.class);
			image.setTablename(img.getTablename());
			image.setTableid(img.getTableid());
			image.setImagepath(img.getImagepath());
			image.setImagename(img.getImagename());
			image.setCreateuser(img.getCreateuser());
			image.setCreateusername(img.getCreateusername());
			image.setCreatedate(img.getCreatedate());
			image.setFuncode(img.getFuncode());
			image.setContype(img.getContype());
			image.setZysqid(img.getZysqid());
			image.setUpdateDate(img.getUpdateDate());
			action.saveEntity(image);
		} catch (HDException e) {
			logger.error(e);
		}

	}

	/**
	 * 复制文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	private boolean copyFile(String oldPath, String newPath) {
		File file = new File(newPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			FileInputStream fis = new FileInputStream(oldPath);
			FileOutputStream fos = new FileOutputStream(newPath);
			byte bt[] = new byte[1024];
			int c;

			try {
				while ((c = fis.read(bt)) > 0) {
					fos.write(bt, 0, c); // 将内容写到新文件当中
				}
				fis.close();
				fos.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

}
