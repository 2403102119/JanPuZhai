package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

/**
 * Function:
 *
 * @author JFL WarwG1@163.com
 * @since 2019/11/4
 */
public class KuaiDiModel extends ResultBean {


    /**
     * info : {  "LogisticCode" : "773015046612912",  "ShipperCode" : "STO",  "Traces" : [ {    "AcceptStation" : "浙江诸暨牌头公司-扫描7-已收件",    "AcceptTime" : "2019-11-18 20:06:44"  }, {    "AcceptStation" : "浙江诸暨牌头公司-已发往-浙江杭州航空部",    "AcceptTime" : "2019-11-18 20:36:37"  }, {    "AcceptStation" : "浙江诸暨牌头公司-已装袋发往-浙江杭州航空部",    "AcceptTime" : "2019-11-18 20:40:02"  }, {    "AcceptStation" : "浙江诸暨牌头公司-已装袋发往-浙江杭州航空部",    "AcceptTime" : "2019-11-18 20:48:43"  }, {    "AcceptStation" : "已到达-浙江杭州航空部",    "AcceptTime" : "2019-11-18 23:47:22"  }, {    "AcceptStation" : "浙江杭州航空部-已装袋发往-河南郑州转运中心",    "AcceptTime" : "2019-11-19 00:03:15"  }, {    "AcceptStation" : "快件已在【河南郑州转运中心】进行卸车，扫描员【狂扫5】",    "AcceptTime" : "2019-11-19 19:25:27"  }, {    "AcceptStation" : "已到达-河南郑州转运中心",    "AcceptTime" : "2019-11-19 19:25:27"  }, {    "AcceptStation" : "河南郑州转运中心-已装袋发往-河南郑州市内集散中心",    "AcceptTime" : "2019-11-19 19:27:29"  }, {    "AcceptStation" : "已到达-河南郑州市内集散中心",    "AcceptTime" : "2019-11-19 20:59:57"  }, {    "AcceptStation" : "河南郑州市内集散中心-已发往-河南郑州第二公司",    "AcceptTime" : "2019-11-19 21:19:08"  }, {    "AcceptStation" : "已到达-河南郑州第二公司",    "AcceptTime" : "2019-11-20 06:55:21"  }, {    "AcceptStation" : "河南郑州第二公司-东四环无点拖车超派-派件中",    "AcceptTime" : "2019-11-20 07:40:21"  }, {    "AcceptStation" : "快件已暂存至郑州任庄站2号楼1单元102店菜鸟驿站，如有疑问请联系15538100347",    "AcceptTime" : "2019-11-20 12:00:31"  }, {    "AcceptStation" : "已签收，签收人凭取货码签收。",    "AcceptTime" : "2019-11-20 19:08:06"  } ],  "State" : "3",  "EBusinessID" : "1602350",  "Success" : true}
     */

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
