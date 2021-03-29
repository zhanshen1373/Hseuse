package com.hd.hse.ss.adapter;

import android.content.Context;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.common.Image;
import com.hd.hse.ss.ImageHandle;
import com.hd.hse.ss.R;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImgAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Image> imageList;
    private LayoutParams mLayoutParams;
    private LinearLayout.LayoutParams mReLayoutParams;
    private boolean isDele = false;
    /**
     * 拍照图片的位置
     */
    private int shotCamraPostion;


    public ImgAdapter(Context context, LayoutParams mLayoutParams, LinearLayout.LayoutParams mReLayoutParams,
                      ArrayList<Image> imageList) {
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mLayoutParams = mLayoutParams;
        this.mReLayoutParams = mReLayoutParams;
        this.imageList = imageList;
        shotCamraPostion = imageList.size();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageList.size() + 1;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return imageList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int arg0, View arg1, ViewGroup arg2) {
        ViewHolder mViewHolder = null;
        if (arg1 == null) {
            mViewHolder = new ViewHolder();
            arg1 = mLayoutInflater.inflate(
                    R.layout.hse_pc_phone_app_layout_surveillance_img_item,
                    null);
            mViewHolder.re = (RelativeLayout) arg1.findViewById(R.id.hse_pc_phone_app_layout_surveillance_re);
            mViewHolder.img = (ImageView) arg1
                    .findViewById(R.id.hse_pc_phone_app_layout_surveillance_img_item_img);
            mViewHolder.imgDele = (ImageView) arg1
                    .findViewById(R.id.hse_pc_phone_app_layout_surveillance_img_item_img_dele);
            arg1.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) arg1.getTag();
        }
        // 设置图片大小

        mViewHolder.img.setLayoutParams(mLayoutParams);
        mViewHolder.re.setLayoutParams(mReLayoutParams);

        mViewHolder.img.setScaleType(ScaleType.FIT_XY);
        if (arg0 != shotCamraPostion) {
            //mViewHolder.img.setRotation(getRotation(imageList.get(arg0).getImagepath()));
            ImageHandle.getInstance().setImageForView(mViewHolder.img, imageList.get(arg0).getImagepath(), mLayoutParams.height, mLayoutParams.width);

        } else {
            mViewHolder.img.setRotation(0);
            mViewHolder.img.setImageResource(R.drawable.hd_hse_photo_add);
        }

        if (isDele && arg0 != shotCamraPostion) {
            mViewHolder.imgDele.setVisibility(View.VISIBLE);

        } else {
            mViewHolder.imgDele.setVisibility(View.GONE);
        }


        mViewHolder.imgDele.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除这张照片，idDele=false,通知更新
                File file = new File(imageList.get(arg0).getImagepath());
                deleteFile(file);
                //从数据库删除数据
                ArrayList<Image> deleImageList = new ArrayList<Image>();
                deleImageList.add(imageList.get(arg0));
                deleteImgFromDB(deleImageList);
                imageList.remove(imageList.get(arg0));
                shotCamraPostion = imageList.size();
                isDele = false;
                notifyDataSetChanged();
            }
        });

        return arg1;
    }

    private class ViewHolder {
        private RelativeLayout re;
        private ImageView img;
        private ImageView imgDele;

    }

    /**
     * 删除文件的方法 Created by liuyang on 2016年3月24日
     *
     * @param file
     */
    private void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            // logger.error("文件不存在");
        }
    }

    /**
     * 从数据库删除照片信息
     * <p>
     * Created by liuyang on 2016年3月24日
     *
     * @param imgs
     */
    private void deleteImgFromDB(List<Image> imgs) {
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            dao.deleteEntities(con, imgs.toArray(new Image[]{}));
            con.commit();
        } catch (SQLException e) {
            //logger.error(e);
            ToastUtils.imgToast(context,
                    R.drawable.hd_hse_common_msg_wrong, "数据库连接异常");
        } catch (DaoException e) {

            ToastUtils.imgToast(context,
                    R.drawable.hd_hse_common_msg_wrong, "删除图片信息失败");
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
    }


    public void setImageList(ArrayList<Image> imageList) {
        this.imageList = imageList;
    }

    public void setDele(boolean isDele) {
        this.isDele = isDele;
    }

    public boolean isDele() {
        return isDele;
    }

    public void setShotCamraPostion(int position) {
        this.shotCamraPostion = position;
    }

    private int getRotation(String path) {
        int rotation = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            String orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            switch (Integer.valueOf(orientation)) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            //logger.error(e.getMessage(), e);
        }
        return rotation;
    }


}
