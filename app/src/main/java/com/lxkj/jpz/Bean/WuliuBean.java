package com.lxkj.jpz.Bean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/6 0006.
 * Describe :
 */

public class WuliuBean{
    /**
     * LogisticCode : 71780507241867
     * ShipperCode : HTKY
     * Traces : [{"AcceptStation":"广州市【广州白云区十九部】，【大岭东/15800144130】已揽收","AcceptTime":"2019-10-13 01:23:47"},{"AcceptStation":"到广州市【广州转运中心】","AcceptTime":"2019-10-13 04:18:36"},{"AcceptStation":"广州市【广州转运中心】，正发往【郑州转运中心】","AcceptTime":"2019-10-13 04:21:35"},{"AcceptStation":"到郑州市【郑州转运中心】","AcceptTime":"2019-10-14 07:56:47"},{"AcceptStation":"郑州市【郑州转运中心】，正发往【郑州金水区二十部】","AcceptTime":"2019-10-14 11:07:55"},{"AcceptStation":"到郑州市【郑州金水区二十部】","AcceptTime":"2019-10-14 13:14:51"},{"AcceptStation":"郑州市【郑州金水区二十部】，【李培森/13676908485】正在派件","AcceptTime":"2019-10-14 13:48:28"},{"AcceptStation":"派件送达【百世邻里】伟业百世邻里，请前往五号楼下百世（水站旁边）领取您的包裹，联系电话：13676908485","AcceptTime":"2019-10-14 14:35:02"},{"AcceptStation":"客户已取件，提货点：【百世邻里】 伟业百世邻里（13676908485 五号楼下百世（水站旁边））","AcceptTime":"2019-10-15 09:08:48"}]
     * State : 3
     * EBusinessID : 1444561
     * Success : true
     */

    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String EBusinessID;
    private boolean Success;
    private List<TracesBean> Traces;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean {
        /**
         * AcceptStation : 广州市【广州白云区十九部】，【大岭东/15800144130】已揽收
         * AcceptTime : 2019-10-13 01:23:47
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}
