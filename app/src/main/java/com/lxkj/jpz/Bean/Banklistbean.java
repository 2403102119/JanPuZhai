package com.lxkj.jpz.Bean;

import com.lxkj.jpz.Http.ResultBean;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class Banklistbean extends ResultBean {

    private List<BanksBean> banks;

    public List<BanksBean> getBanks() {
        return banks;
    }

    public void setBanks(List<BanksBean> banks) {
        this.banks = banks;
    }

    public static class BanksBean {
        /**
         * bankname : xxx
         * bankno : xxx
         */

        private String bankname;
        private String bankno;

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getBankno() {
            return bankno;
        }

        public void setBankno(String bankno) {
            this.bankno = bankno;
        }
    }
}
