package com.hd.hse.ss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.LruCache;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageHandle {

    private ExecutorService executorService = Executors
            .newSingleThreadExecutor(); // 单个线程来执行任务
    private LruCache<String, Bitmap> mMemoryCache;
    private android.os.Handler handler = new android.os.Handler();
    private static ImageHandle instance;

    public static ImageHandle getInstance() {
        if (instance == null) {
            synchronized (ImageHandle.class) {
                if (instance == null) {
                    instance = new ImageHandle();
                }
            }

        }
        return instance;
    }

    private ImageHandle() {
        // 获取可用的内存最大值
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        // 使用最大可用内存值的1/8作为缓存的大小
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量
                return value.getByteCount();
            }
        };
    }

    private void addBitmapToMemory(String imgPath, Bitmap bitmap) {
        if (getBitmapFromMemoryFromCache(imgPath) == null) {
            mMemoryCache.put(imgPath, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryFromCache(String imgPath) {
        return mMemoryCache.get(imgPath);
    }

    /**
     * 对外提供的方法
     *
     * @author yn
     */
    public void setImageForView(ImageView iv, String imagePath, int width,
                                int height) {
        iv.setTag(imagePath);
        // 缓存中存在图片
        Bitmap bitmap = getBitmapFromMemoryFromCache(imagePath);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
        } else {
            //加入线程队列
            executorService.submit(new ImagePressRunnable(iv, imagePath, width, height));
        }
    }

    /**
     * 停止线程池所有线程操作
     */
    public void shutDownNow() {
        //executorService.shutdownNow();
    }

    public class ImagePressRunnable implements Runnable {
        String imagePath;
        int width;
        int height;
        ImageView iv;

        public ImagePressRunnable(ImageView iv, String imagePath, int width,
                                  int height) {
            this.imagePath = imagePath;
            this.width = width;
            this.height = height;
            this.iv = iv;
        }

        @Override
        public void run() {
            final Bitmap bitmap = getImageThumbnail(imagePath, width, height);
            addBitmapToMemory(imagePath, bitmap);
            if (iv.getTag() != null && iv.getTag().equals(imagePath)) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bitmap);
                    }
                });
            }
        }

    }

    /**
     * 压缩图片为指定大小
     *
     * @param imagePath
     * @param width
     * @param height
     * @return
     */
    private Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return bitmap2;
    }


}
