package com.hd.hse.common.module.phone.ui.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hd.hse.system.SystemProperty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.Handler;

/**
 * 
 * @author yn图片压缩类
 * 
 */
public class ImageCompress {
	private OnCompressListener mListener;
	private final String notFindTempFile = "没有找到照片";
	// private String savePath;
	private File tempFile;
	private Handler handler = new Handler();

	public ImageCompress(File tempFile) {
		// this.savePath = savePath;
		this.tempFile = tempFile;
	}

	/**
	 * 设置监听
	 * 
	 * @param mListener
	 */
	public void setOnCompressListener(OnCompressListener mListener) {
		this.mListener = mListener;
	}

	/**
	 * 压缩图片，对外方法接口
	 */
	public void compressImage() {
		if (tempFile == null || !tempFile.exists()) {
			if (mListener != null) {
				mListener.onError(notFindTempFile);
			}
			return;

		}

		compressImageInThread(tempFile);
	}

	/**
	 * @param tempFile
	 *            sd卡中保存的临时，未处理照片
	 * @param savePath
	 *            图片压缩后的保存路径
	 */
	private void compressImageInThread(final File tempFile) {
		if (mListener != null) {
			mListener.onStart();
		}
		new Thread() {
			public void run() {
				thirdCompress(tempFile);
			};
		}.start();
	}

	/**
	 * obtain the image rotation angle
	 * 
	 * @param path
	 *            path of target image
	 */
	private int getImageSpinAngle(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * obtain the image's width and height
	 * 
	 * @param imagePath
	 *            the path of image
	 */
	public int[] getImageSize(String imagePath) {
		int[] res = new int[2];

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inSampleSize = 1;
		BitmapFactory.decodeFile(imagePath, options);

		res[0] = options.outWidth;
		res[1] = options.outHeight;

		return res;
	}

	private void thirdCompress(final File file) {
		// String thumb = savePath;

		double size;
		String filePath = file.getAbsolutePath();

		int angle = getImageSpinAngle(filePath);
		int imageSize[] = getImageSize(filePath);
		int width = imageSize[0];
		int height = imageSize[1];
		int thumbW = width % 2 == 1 ? width + 1 : width;
		int thumbH = height % 2 == 1 ? height + 1 : height;

		width = thumbW > thumbH ? thumbH : thumbW;
		height = thumbW > thumbH ? thumbW : thumbH;

		double scale = ((double) width / height);

		if (scale <= 1 && scale > 0.5625) {
			if (height < 1664) {
				if (file.length() / 1024 < 150) {
					if (mListener != null) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								mListener.onSuccess(file);
							}
						});

					}
					return;

				}

				size = (width * height) / Math.pow(1664, 2) * 150;
				size = size < 60 ? 60 : size;
			} else if (height >= 1664 && height < 4990) {
				thumbW = width / 2;
				thumbH = height / 2;
				size = (thumbW * thumbH) / Math.pow(2495, 2) * 300;
				size = size < 60 ? 60 : size;
			} else if (height >= 4990 && height < 10240) {
				thumbW = width / 4;
				thumbH = height / 4;
				size = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
				size = size < 100 ? 100 : size;
			} else {
				int multiple = height / 1280 == 0 ? 1 : height / 1280;
				thumbW = width / multiple;
				thumbH = height / multiple;
				size = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
				size = size < 100 ? 100 : size;
			}
		} else if (scale <= 0.5625 && scale > 0.5) {
			if (height < 1280 && file.length() / 1024 < 200) {
				if (mListener != null) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							mListener.onSuccess(file);
						}
					});
				}
				return;
			}

			int multiple = height / 1280 == 0 ? 1 : height / 1280;
			thumbW = width / multiple;
			thumbH = height / multiple;
			size = (thumbW * thumbH) / (1440.0 * 2560.0) * 400;
			size = size < 100 ? 100 : size;
		} else {
			int multiple = (int) Math.ceil(height / (1280.0 / scale));
			thumbW = width / multiple;
			thumbH = height / multiple;
			size = ((thumbW * thumbH) / (1280.0 * (1280 / scale))) * 500;
			size = size < 100 ? 100 : size;
		}

		compress(filePath, thumbW, thumbH, angle, (long) size);

	}

	private void compress(String largeImagePath, 
			int width, int height, int angle, long size) {
		Bitmap thbBitmap = compress(largeImagePath, width, height);
		thbBitmap = rotatingImage(angle, thbBitmap);
		saveImage(thbBitmap, size);
	}

	private Bitmap compress(String imagePath, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);

		int outH = options.outHeight;
		int outW = options.outWidth;
		int inSampleSize = 1;

		if (outH > height || outW > width) {
			int halfH = outH / 2;
			int halfW = outW / 2;

			while ((halfH / inSampleSize) > height
					&& (halfW / inSampleSize) > width) {
				inSampleSize *= 2;
			}
		}

		options.inSampleSize = inSampleSize;

		options.inJustDecodeBounds = false;

		int heightRatio = (int) Math.ceil(options.outHeight / (float) height);
		int widthRatio = (int) Math.ceil(options.outWidth / (float) width);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				options.inSampleSize = heightRatio;
			} else {
				options.inSampleSize = widthRatio;
			}
		}
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(imagePath, options);
	}

	private void saveImage(Bitmap bitmap, long size) {

		

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int options = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);

		while (stream.toByteArray().length / 1024 > size && options > 6) {
			stream.reset();
			options -= 6;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
		}

		bitmap.recycle();
		final String tempPath=tempFile.getAbsolutePath();
		if (tempFile.exists()) {
			tempFile.delete();
		}
		try {
			FileOutputStream fos = new FileOutputStream(tempPath);
			fos.write(stream.toByteArray());
			fos.flush();
			fos.close();
			stream.close();
			if (mListener != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mListener.onSuccess(new File(tempPath));
					}
				});

			}
		} catch (final IOException e) {
			e.printStackTrace();
			if (mListener != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mListener.onError(e.toString());
					}
				});

			}
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

	private static Bitmap rotatingImage(int angle, Bitmap bitmap) {
		// rotate image
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// create a new image
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	/**
	 * 回调接口
	 */
	public interface OnCompressListener {
		public void onStart();

		public void onSuccess(File file);

		public void onError(String error);
	}
}
