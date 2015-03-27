package com.yun9.mobile.position.iface;

import com.yun9.mobile.framework.http.Response;

import java.io.Serializable;


public interface MapCallBack extends Serializable {
    public void onSuccess(OutParam outParm);
    public void onFailure();

    class OutParam{

        private double longitude;

        private double latitude;

        private String city;

        private String addr;

        /**
         * @param longitude
         * @param latitude
         * @param city
         * @param addr
         */
        public OutParam(double longitude, double latitude, String city,
                String addr) {
            super();
            this.longitude = longitude;
            this.latitude = latitude;
            this.city = city;
            this.addr = addr;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getCity() {
            return city;
        }

        public String getAddr() {
            return addr;
        }



    }
}
