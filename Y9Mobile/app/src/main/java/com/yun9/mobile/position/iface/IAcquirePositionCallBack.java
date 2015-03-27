package com.yun9.mobile.position.iface;

import com.yun9.mobile.framework.http.Response;

import java.io.Serializable;


public interface IAcquirePositionCallBack extends Serializable {
    public void onSuccess(OutParam outParm);
    public void onFailure();

    class OutParam{

        private double longitude;

        private double latitude;

        private String city;

        private String addr;
        
        private String poiName;
     
        /**
		 * @param longitude
		 * @param latitude
		 * @param city
		 * @param addr
		 * @param poiName
		 */
		public OutParam(double longitude, double latitude, String city,
				String addr, String poiName) {
			super();
			this.longitude = longitude;
			this.latitude = latitude;
			this.city = city;
			this.addr = addr;
			this.poiName = poiName;
		}

		/**
         * @param longitude
         * @param latitude
         * @param city
         * @param addr
         */
        private OutParam(double longitude, double latitude, String city,
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

		public String getPoiName() {
			return poiName;
		}

		public void setPoiName(String poiName) {
			this.poiName = poiName;
		}



    }
}
