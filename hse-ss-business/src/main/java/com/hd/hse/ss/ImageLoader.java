package com.hd.hse.ss;

import android.os.Handler;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.common.Image;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ImageLoader {

    private Handler handler = new Handler();
    private String iPAndPort;
    private ImageCallback callback;
    private ArrayList<Image> images;
    private String rzid;
    private boolean isCancel;

    public ImageLoader(ArrayList<Image> images, String rzid) {
        this.rzid = rzid;
        this.images = images;
        QueryWorkInfo queryWorkInfo = new QueryWorkInfo();
        try {
            iPAndPort = "http://" + queryWorkInfo.queryIPAndPort(null)
                    + "/";
        } catch (HDException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行图片下载任务
     */
    public void execute() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (Image image : images) {
                    if (!isCancel) {
                        String urlPath = iPAndPort + image.getImagepath();
                        String path = SystemProperty
                                .getSystemProperty().getRootDBpath()
                                + File.separator +
                                rzid + File.separator + image.getImagename();
                        saveNetDataToSd(urlPath, path, image);
                    }
                }
                if (!isCancel && callback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onImageAllLoaded();
                        }
                    });

                }
            }
        }.start();
    }

    /**
     * 取消下载
     */
    public void isCancel() {
        isCancel = true;
    }


    private void saveNetDataToSd(String urlpath, String path, final Image image) {
        File file = new File(path);
        if (file.exists()) {
            return;
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(8000);
            conn.setConnectTimeout(8000);
            InputStream in = conn.getInputStream();
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[100 * 1024];
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            in.close();
            fos.flush();
            if (callback != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onImageLoaded(image);
                    }
                });

            }

        } catch (IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {

                    if (callback != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onImageLoadErr(image);
                            }
                        });

                    }
                }
            });

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setImageCallback(ImageCallback callback) {
        this.callback = callback;
    }

    /**
     * 对外界开放的回调接口
     */
    public interface ImageCallback {
        // 注意 此方法是用来设置目标对象的图像资源
        public void onImageLoaded(Image img);

        public void onImageLoadErr(Image img);

        public void onImageAllLoaded();
    }
}
