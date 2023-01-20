package com.dhx.gulimall.common.constant;

import lombok.Data;

/**
 * @author dhx_
 * @className WareConstant
 * @date : 2023/01/20/ 21:32
 **/
@Data
public class WareConstant {
    public enum PurchaseStatusEnum{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVE(2,"已领取"),
        FINISH(3,"已完成"),
        HASERROR(4,"有异常"),
        ;
        private int code;
        private String msg;
        PurchaseStatusEnum(int code,String msg){
            this.code=code;
            this.msg=msg;
        }

        public Integer getCode() {
            return this.code;
        }
        public String getMsg() {
            return this.msg;
        }
    }

    public enum PurchaseDetailStatusEnum{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        BUYING(2,"正在采购"),
        FINISH(3,"已完成"),
        HASERROR(4,"采购失败"),
        ;
        private int code;
        private String msg;
        PurchaseDetailStatusEnum(int code,String msg){
            this.code=code;
            this.msg=msg;
        }

        public Integer getCode() {
            return this.code;
        }
        public String getMsg() {
            return this.msg;
        }
    }
}
