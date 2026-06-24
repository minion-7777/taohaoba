package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class UserWithdrawalInfoBean {

    /**
     * list : [{"created_time":"2025-04-25 17:17:00","updated_time":"2025-04-25 17:17:00","id":3,"withdrawal_no":"","user_id":58,"wallet_id":36,"type":1,"name":"","account":"","bank":"","amount":20,"fee":0,"status":0,"status_zh":"待审核"}]
     * total : 1
     */

    private Integer total;
    private Integer sum;
    private List<ListDTO> list;

    public static UserWithdrawalInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, UserWithdrawalInfoBean.class);
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public static class ListDTO {
        /**
         * created_time : 2025-04-25 17:17:00
         * updated_time : 2025-04-25 17:17:00
         * id : 3
         * withdrawal_no :
         * user_id : 58
         * wallet_id : 36
         * type : 1
         * name :
         * account :
         * bank :
         * amount : 20
         * fee : 0
         * status : 0
         * status_zh : 待审核
         */

        private String created_time;
        private String updated_time;
        private Integer id;
        private String withdrawal_no;
        private Integer user_id;
        private Integer wallet_id;
        private Integer type;
        private String name;
        private String account;
        private String bank;
        private Integer amount;
        private Integer fee;
        private Integer status;
        private String status_zh;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUpdated_time() {
            return updated_time;
        }

        public void setUpdated_time(String updated_time) {
            this.updated_time = updated_time;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getWithdrawal_no() {
            return withdrawal_no;
        }

        public void setWithdrawal_no(String withdrawal_no) {
            this.withdrawal_no = withdrawal_no;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getWallet_id() {
            return wallet_id;
        }

        public void setWallet_id(Integer wallet_id) {
            this.wallet_id = wallet_id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getFee() {
            return fee;
        }

        public void setFee(Integer fee) {
            this.fee = fee;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatus_zh() {
            return status_zh;
        }

        public void setStatus_zh(String status_zh) {
            this.status_zh = status_zh;
        }
    }
}
