package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class buySellGoodsBean {

    /**
     * buy_order_count : {"pending_count":3,"trade_count":0,"complete_count":0,"cancel_count":0}
     * sell_order_count : {"pending_count":4,"trade_count":0,"complete_count":0,"cancel_count":1}
     * user_goods_count : {"sell_count":1,"lower_count":0,"pending_count":0,"fail_count":0}
     */

    private BuyOrderCountDTO buy_order_count;
    private SellOrderCountDTO sell_order_count;
    private UserGoodsCountDTO user_goods_count;

    public static buySellGoodsBean objectFromData(String str) {

        return new Gson().fromJson(str, buySellGoodsBean.class);
    }

    public BuyOrderCountDTO getBuy_order_count() {
        return buy_order_count;
    }

    public void setBuy_order_count(BuyOrderCountDTO buy_order_count) {
        this.buy_order_count = buy_order_count;
    }

    public SellOrderCountDTO getSell_order_count() {
        return sell_order_count;
    }

    public void setSell_order_count(SellOrderCountDTO sell_order_count) {
        this.sell_order_count = sell_order_count;
    }

    public UserGoodsCountDTO getUser_goods_count() {
        return user_goods_count;
    }

    public void setUser_goods_count(UserGoodsCountDTO user_goods_count) {
        this.user_goods_count = user_goods_count;
    }

    public static class BuyOrderCountDTO {
        /**
         * pending_count : 3
         * trade_count : 0
         * complete_count : 0
         * cancel_count : 0
         */

        private Integer pending_count;
        private Integer trade_count;
        private Integer complete_count;
        private Integer cancel_count;

        public static BuyOrderCountDTO objectFromData(String str) {

            return new Gson().fromJson(str, BuyOrderCountDTO.class);
        }

        public Integer getPending_count() {
            return pending_count;
        }

        public void setPending_count(Integer pending_count) {
            this.pending_count = pending_count;
        }

        public Integer getTrade_count() {
            return trade_count;
        }

        public void setTrade_count(Integer trade_count) {
            this.trade_count = trade_count;
        }

        public Integer getComplete_count() {
            return complete_count;
        }

        public void setComplete_count(Integer complete_count) {
            this.complete_count = complete_count;
        }

        public Integer getCancel_count() {
            return cancel_count;
        }

        public void setCancel_count(Integer cancel_count) {
            this.cancel_count = cancel_count;
        }
    }

    public static class SellOrderCountDTO {
        /**
         * pending_count : 4
         * trade_count : 0
         * complete_count : 0
         * cancel_count : 1
         */

        private Integer pending_count;
        private Integer trade_count;
        private Integer complete_count;
        private Integer cancel_count;

        public static SellOrderCountDTO objectFromData(String str) {

            return new Gson().fromJson(str, SellOrderCountDTO.class);
        }

        public Integer getPending_count() {
            return pending_count;
        }

        public void setPending_count(Integer pending_count) {
            this.pending_count = pending_count;
        }

        public Integer getTrade_count() {
            return trade_count;
        }

        public void setTrade_count(Integer trade_count) {
            this.trade_count = trade_count;
        }

        public Integer getComplete_count() {
            return complete_count;
        }

        public void setComplete_count(Integer complete_count) {
            this.complete_count = complete_count;
        }

        public Integer getCancel_count() {
            return cancel_count;
        }

        public void setCancel_count(Integer cancel_count) {
            this.cancel_count = cancel_count;
        }
    }

    public static class UserGoodsCountDTO {
        /**
         * sell_count : 1
         * lower_count : 0
         * pending_count : 0
         * fail_count : 0
         */

        private Integer sell_count;
        private Integer lower_count;
        private Integer pending_count;
        private Integer fail_count;

        public static UserGoodsCountDTO objectFromData(String str) {

            return new Gson().fromJson(str, UserGoodsCountDTO.class);
        }

        public Integer getSell_count() {
            return sell_count;
        }

        public void setSell_count(Integer sell_count) {
            this.sell_count = sell_count;
        }

        public Integer getLower_count() {
            return lower_count;
        }

        public void setLower_count(Integer lower_count) {
            this.lower_count = lower_count;
        }

        public Integer getPending_count() {
            return pending_count;
        }

        public void setPending_count(Integer pending_count) {
            this.pending_count = pending_count;
        }

        public Integer getFail_count() {
            return fail_count;
        }

        public void setFail_count(Integer fail_count) {
            this.fail_count = fail_count;
        }
    }
}
