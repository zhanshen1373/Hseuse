package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;

import java.util.List;


public class SxtBean extends SuperEntity {






    /**
     * location : WZ160599
     * cameras : [{"rtsp":"rtsp://admin:xxzx2020++++@10.105.1.34:554/cam/realmonitor?channel=100&subtype=1","name":"摄像头名称1"},{"rtsp":"rtsp://admin:xxzx2020++++@10.105.1.34:554/cam/realmonitor?channel=173&subtype=1","name":"摄像头名称2"}]
     */

    private String location;
    private List<CamerasBean> cameras;
    private List<WorkOrder> order;



    public List<WorkOrder> getOrder() {
        return order;
    }

    public void setOrder(List<WorkOrder> order) {
        this.order = order;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<CamerasBean> getCameras() {
        return cameras;
    }

    public void setCameras(List<CamerasBean> cameras) {
        this.cameras = cameras;
    }


    public static class CamerasBean extends SuperEntity {
        /**
         * rtsp : rtsp://admin:xxzx2020++++@10.105.1.34:554/cam/realmonitor?channel=100&subtype=1
         * name : 摄像头名称1
         */

        private String rtsp;
        private String name;

        public String getRtsp() {
            return rtsp;
        }

        public void setRtsp(String rtsp) {
            this.rtsp = rtsp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
