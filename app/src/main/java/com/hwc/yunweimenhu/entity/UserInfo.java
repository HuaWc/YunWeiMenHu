package com.hwc.yunweimenhu.entity;

import java.io.Serializable;

/**
 * @author Administrator
 *         日期 2018/8/8
 *         描述 用户信息
 */

public class UserInfo implements Serializable {
    private static final long serialVersionUID = -5683263669918171030L;

    /**
     * cookiekey : 70b9___ewei_shopv2_member_session_1
     * cookievalue : eyJpZCI6IjIxOTAiLCJvcGVuaWQiOiJ3YXBfdXNlcl8xXzE1Mzg1MTM2NzcyIiwibW9iaWxlIjoiMTUzODUxMzY3NzIiLCJwd2QiOiIyZjVjOTU0OWE2YTc3ZTMxOTYxZGI1Mjk1YWJmYmQ1OSIsInNhbHQiOiJmNmd6eFZReUM3bnFYU2NjIiwiZXdlaV9zaG9wdjJfbWVtYmVyX2hhc2giOiIyNzM1ODVlMDliZDc5YWQwNjY4YzFiNGY0MWVkYzBkNyJ9
     */

    private String cookiekey;
    private String cookievalue;
    /**
     * credit2 : 0.00
     * mobile : 15385136772
     * nickname : 153xxxx6772
     * realname :
     */

    private String credit2;
    private String mobile;
    private String nickname;
    private String realname;
    /**
     * avatar :
     * ordernum : {"status0":"1","status1":"2","status2":"0","status4":"0"}
     */

    private String avatar;
    private OrdernumBean ordernum;


    public String getCookiekey() {
        return cookiekey;
    }

    public void setCookiekey(String cookiekey) {
        this.cookiekey = cookiekey;
    }

    public String getCookievalue() {
        return cookievalue;
    }

    public void setCookievalue(String cookievalue) {
        this.cookievalue = cookievalue;
    }


    public String getCredit2() {
        return credit2;
    }

    public void setCredit2(String credit2) {
        this.credit2 = credit2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public OrdernumBean getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(OrdernumBean ordernum) {
        this.ordernum = ordernum;
    }

    public static class OrdernumBean implements Serializable {
        /**
         * status0 : 1
         * status1 : 2
         * status2 : 0
         * status4 : 0
         */

        private String status0;
        private String status1;
        private String status2;
        private String status4;

        public String getStatus0() {
            return status0;
        }

        public void setStatus0(String status0) {
            this.status0 = status0;
        }

        public String getStatus1() {
            return status1;
        }

        public void setStatus1(String status1) {
            this.status1 = status1;
        }

        public String getStatus2() {
            return status2;
        }

        public void setStatus2(String status2) {
            this.status2 = status2;
        }

        public String getStatus4() {
            return status4;
        }

        public void setStatus4(String status4) {
            this.status4 = status4;
        }
    }
}
