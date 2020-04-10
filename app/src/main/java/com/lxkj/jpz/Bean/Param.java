package com.lxkj.jpz.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class Param {

    private List<SpecBean> spec;
    private List<SkuBean> sku;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;

    public String getStartbuy() {
        return startbuy;
    }

    public void setStartbuy(String startbuy) {
        this.startbuy = startbuy;
    }

    private String startbuy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;

    public List<SpecBean> getSpec() {
        return spec;
    }

    public void setSpec(List<SpecBean> spec) {
        this.spec = spec;
    }

    public List<SkuBean> getSku() {
        return sku;
    }

    public void setSku(List<SkuBean> sku) {
        this.sku = sku;
    }

    public static class SpecBean {
        /**
         * specName : 颜色
         * specValue : ["黑色","红色","粉色","白色","蓝色"]
         */

        private String specName;
        private List<String> specValue;

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public List<String> getSpecValue() {
            return specValue;
        }

        public void setSpecValue(List<String> specValue) {
            this.specValue = specValue;
        }
    }

    public static class SkuBean {
        /**
         * inventoryCount : 0
         * id : 355
         * spec : ["黑色","80g"]
         */

        private int inventoryCount;
        private String id;

        public String getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(String skuPrice) {
            this.skuPrice = skuPrice;
        }

        private String skuPrice;
        private List<String> spec;

        public SkuBean(int inventoryCount, String skuPrice,String id, List<String> spec) {
            this.inventoryCount = inventoryCount;
            this.id = id;
            this.skuPrice = skuPrice;
            this.spec = spec;
        }

        public int getInventoryCount() {
            return inventoryCount;
        }

        public void setInventoryCount(int inventoryCount) {
            this.inventoryCount = inventoryCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getSpec() {
            return spec;
        }

        public void setSpec(List<String> spec) {
            this.spec = spec;
        }
    }
}
