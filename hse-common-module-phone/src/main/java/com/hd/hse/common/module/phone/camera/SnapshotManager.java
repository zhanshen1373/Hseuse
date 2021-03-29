package com.hd.hse.common.module.phone.camera;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.common.Image;
import com.hd.hse.system.SystemProperty;

@SuppressWarnings("all")
public class SnapshotManager {

	public final static String TAG = "SnapshotManager";

	public interface SnapshotListener {
		public void onSnapshotSavedSuccess();

		public void onSnapshotSavedFailed();
	}

	private SnapshotListener mListener;

	public void setSnapshotListener(SnapshotListener listener) {
		this.mListener = listener;
	}

	private CameraManager mCameraManager;
	private boolean mWaitExposureSettle;

	public SnapshotManager(CameraManager cameraManager) {
		this.mCameraManager = cameraManager;
	}

	private Camera.PictureCallback mJpegPictureCallback = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(final byte[] jpegData, Camera camera) {
			mCameraManager.restartPreviewIfNeeded();

			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Toast.makeText(mCameraManager.getContext(), "SD卡不可用！",
						Toast.LENGTH_SHORT).show();
				return;
			}

			new Thread() {
				public void run() {
					String createTime = SystemProperty.getSystemProperty()
							.getSysSHDateTime();
					if (mImageEntity.getChildids() != null
							&& !mImageEntity.getChildids().equals("")) {
						// 合并
						String[] orderIds = mImageEntity.getTableid()
								.split(",");
						for (int i = 0; i < orderIds.length; i++) {
							mImageEntity.setTableid(orderIds[i]);
							mImageEntity.setZysqid(orderIds[i]);
							mImageEntity.setImagepath(SystemProperty
									.getSystemProperty().getRootDBpath()
									+ File.separator + orderIds[i]);
							saveImage(mImageEntity, jpegData, createTime);
						}
					} else {
						// 非合并
						saveImage(mImageEntity, jpegData, createTime);
					}
				}
			}.start();
		}
	};

	private void saveImage(Image mImageEntity, byte[] jpegData,
			String createTime) {
		Log.i(TAG, "相片数据库存储开始......");
		String tableName = (String) mImageEntity.getAttribute("tablename");
		String tableId = (String) mImageEntity.getAttribute("tableid");
		String zysqId = (String) mImageEntity.getAttribute("zysqid");
		String filePath = (String) mImageEntity.getAttribute("imagepath");
		String imageName = (String) mImageEntity.getAttribute("imagename");
		String createuser = (String) mImageEntity.getAttribute("createuser");
		String createusername = (String) mImageEntity
				.getAttribute("createusername");
		String funcode = (String) mImageEntity.getAttribute("funcode");
		Integer contype = (Integer) mImageEntity.getAttribute("contype");
		String childids = (String) mImageEntity.getAttribute("childids");
		String dbSaveTime = SystemProperty.getSystemProperty().getSysDateTime();
		String fileName = imageName + "_" + createTime + ".jpg";
		String path = filePath + File.separator + fileName;

		mImageEntity.setAttribute("path",fileName);
		Log.i(TAG, path);

		Image tempImage = null;
		BusinessAction businessAction = null;
		try {
			businessAction = new BusinessAction();
			tempImage = (Image) businessAction.addEntity(Image.class);
			tempImage.setAttribute("tablename", tableName);
			tempImage.setAttribute("tableid", tableId);
			tempImage.setAttribute("zysqid", zysqId);
			tempImage.setAttribute("imagepath", path);
			tempImage.setAttribute("imagename", fileName);
			tempImage.setAttribute("createdate", dbSaveTime);
			tempImage.setAttribute("createuser", createuser);
			tempImage.setAttribute("createusername", createusername);
			tempImage.setAttribute("funcode", funcode);
			tempImage.setAttribute("contype", contype);
			tempImage.setAttribute("childids", childids);
		} catch (HDException ex) {
			// TODO
		}

		Log.i(TAG, "相片文件存储开始......");
		File dirFile = new File(filePath);

		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		int orientation = mCameraManager.getOrientation();
		File targetFile = new File(path);
		Bitmap bm = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length);
		Bitmap bm2 = rotateBitmapByDegree(bm, orientation);

		try {
			FileOutputStream fot;
			fot = new FileOutputStream(targetFile);
			bm2.compress(Bitmap.CompressFormat.JPEG, 100, fot);
			businessAction.saveEntity(tempImage);
			if (mListener != null) {
				mListener.onSnapshotSavedSuccess();
			}
		} catch (HDException e1) {
			e1.printStackTrace();
		}catch (FileNotFoundException e) {
			Log.i(TAG, "相片存储---文件无法找到");
			if (mListener != null) {
				mListener.onSnapshotSavedFailed();
			}
			e.printStackTrace();
		}

		/*try {
			FileOutputStream fos = new FileOutputStream(targetFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(jpegData);
			bos.close();
			fos.close();

			Log.i(TAG, orientation + "");
			Log.i(TAG, targetFile.getAbsolutePath());
			ExifInterface exifInterface = new ExifInterface(
					targetFile.getAbsolutePath());
			int currentOrientation = ExifInterface.ORIENTATION_NORMAL;
			switch (orientation) {
			case 90:
				currentOrientation = ExifInterface.ORIENTATION_ROTATE_90;
				break;

			case 180:
				currentOrientation = ExifInterface.ORIENTATION_ROTATE_180;
				break;

			case 270:
				currentOrientation = ExifInterface.ORIENTATION_ROTATE_270;
				break;

			case 360:
				currentOrientation = ExifInterface.ORIENTATION_NORMAL;
				break;
			}
			Log.i(TAG, currentOrientation + "---");
			exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION,
					currentOrientation + "");
			exifInterface.saveAttributes();
			businessAction.saveEntity(tempImage);

			// String targetPath = targetFile.getAbsolutePath();
			// Bitmap bitmap =
			// compressImageByPixel(targetFile.getAbsolutePath());
			// compressImageByPixel(targetPath);

			if (mListener != null) {
				mListener.onSnapshotSavedSuccess();
			}
		} catch (FileNotFoundException e) {
			Log.i(TAG, "相片存储---文件无法找到");
			if (mListener != null) {
				mListener.onSnapshotSavedFailed();
			}
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, "相片存储---文件传输出错");
			if (mListener != null) {
				mListener.onSnapshotSavedFailed();
			}
			e.printStackTrace();
		} catch (Exception e) {

		}*/
	}

	/**
	 * 将图片按照某个角度进行旋转
	 * 
	 * @param bm
	 *            需要旋转的图片
	 * @param degree
	 *            旋转角度
	 * @return 旋转后的图片
	 */
	private Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		Bitmap returnBm = null;

		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}

	private Runnable mCaptureRunnable = new Runnable() {
		public void run() {
			if (mWaitExposureSettle) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mCameraManager.takeSnapshot(null, null, mJpegPictureCallback);
		}
	};

	private Image mImageEntity;

	public void queueSnapshot(Image imageEntity) {
		this.mImageEntity = imageEntity;
		new Thread(mCaptureRunnable).start();
	}

	public static Bitmap compressImageByPixel(String imgPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true; // 只读取宽高，而不读取内容
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
		int width = newOpts.outWidth;
		int height = newOpts.outHeight;
		float maxSize = 1000.0f; // 默认1000px
		int sampleSize = 1;
		if (width > height && width > maxSize) {
			sampleSize = (int) (newOpts.outWidth / maxSize);
		} else if (width < height && height > maxSize) {
			sampleSize = (int) (newOpts.outHeight / maxSize);
		}
		sampleSize++;
		newOpts.inJustDecodeBounds = false;
		newOpts.inSampleSize = sampleSize; // 设置采用率
		newOpts.inPreferredConfig = Config.ARGB_8888; // 设置格式，默认，一般不改
		newOpts.inPurgeable = true; // 这个标志，在使用decodeFile，decodeResource被忽略
		newOpts.inInputShareable = true; // 当系统内存不足时，图片自动回收
		return BitmapFactory.decodeFile(imgPath, newOpts);
	}

	public static void compressImageByQuality(final Bitmap bitmap,
			final String imgPath) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断，如果压缩后图片仍然大于100kb，继续压缩
			baos.reset();
			quality -= 10;
			if (quality < 0) {
				quality = 0;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			if (quality == 0) {
				break;
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream(new File(imgPath));
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}