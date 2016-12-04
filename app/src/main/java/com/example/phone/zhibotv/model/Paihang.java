package com.example.phone.zhibotv.model;

import java.util.List;

/**
 * Created by my on 2016/12/2.
 */
public class Paihang {

    /**
     * ranking : {"total":[{"name":"豪仔","pic":"/uploads/imgs/2016/03-25/83664b1eff3400703acbe26b0a5590fa.jpg","points":"3380","rank":"1","richlevel":"8","rnum":"30092136","sex":"1","uid":"2488a7d9785852e085b95b8fccdaae1d"},{"name":"纬:回梦游仙织梦行云","pic":"/uploads/imgs/2016/09-30/59c0966d6e56be36f85058f17e13416d.jpg","points":"2000","rank":"2","richlevel":"11","rnum":"30130437","sex":"1","uid":"535bf6e3bf6e0a8a59552f93769c1de3"},{"name":"王家未。","pic":"/uploads/imgs/2016/04-20/1461143031lsgi2.jpg","points":"1800","rank":"3","richlevel":"1","rnum":"30277241","sex":"1","uid":"2e1615eddd0e44f95b93b2c6ee69016b"},{"name":"小虾米","pic":"/uploads/imgs/2016/01-12/1452599955jcttl.jpg","points":"1750","rank":"4","richlevel":"9","rnum":"30000075","sex":"1","uid":"385d2f4e0a5743642cdec0af6da68267"},{"name":"Dan.","pic":"/uploads/imgs/2016/05-07/1462588156x9z44.jpg","points":"1700","rank":"5","richlevel":"1","rnum":"30407511","sex":"1","uid":"1c7fd96a06f55a8afe219fe7d9030659"},{"name":"shimata","pic":"/uploads/imgs/2016/05-08/1462684391cjgpp.jpg","points":"600","rank":"6","richlevel":"0","rnum":"30414217","sex":"1","uid":"86714db83fe7ff5edc4fceaf8bfec4cd"},{"name":"ミ洄忆dē\u201c独奏","pic":"/uploads/imgs/2016/05-07/1462600451slkv4.jpg","points":"600","rank":"7","richlevel":"0","rnum":"30409609","sex":"1","uid":"8969d43f36c4edc20edfa72f5699360e"},{"name":"二中 王飞","pic":"/images/video_user.png","points":"600","rank":"8","richlevel":"0","rnum":"30407703","sex":"1","uid":"ca8b9fcac7c3ad9f3386b35e9e86debc"},{"name":"二中李文静","pic":"/uploads/imgs/2016/05-07/14625872571v7fp.jpg","points":"585","rank":"9","richlevel":"0","rnum":"30407458","sex":"1","uid":"cbf3a2a85372e3f20cbfb24fa14a4c0e"},{"name":"小小i","pic":"/uploads/imgs/2016/05-07/a816f4c3e9cfd26c741ddfd54eb58b86.jpg","points":"565","rank":"10","richlevel":"0","rnum":"30409038","sex":"1","uid":"84d46373b7217432078f01e0b3384ae8"}],"week":[{"name":"读秀图书","pic":"/uploads/imgs/2016/11-26/1480138314d6kwv.jpg","points":"50","rank":"1","richlevel":"0","rnum":"31296516","sex":"1","uid":"75f1add00410d815b71adbba82d45de5"},{"name":"追求简单的小幸福(▲)30770968","pic":"/uploads/imgs/2016/07-12/14683330743ft8b.jpg","points":"5","rank":"2","richlevel":"0","rnum":"30770968","sex":"1","uid":"8c1b6c897eb07b73e66f75a4df70cf13"}]}
     */

    private RankingBean ranking;

    public RankingBean getRanking() {
        return ranking;
    }

    public void setRanking(RankingBean ranking) {
        this.ranking = ranking;
    }

    public static class RankingBean {
        private List<TotalBean> total;
        private List<WeekBean> week;

        public List<TotalBean> getTotal() {
            return total;
        }

        public void setTotal(List<TotalBean> total) {
            this.total = total;
        }

        public List<WeekBean> getWeek() {
            return week;
        }

        public void setWeek(List<WeekBean> week) {
            this.week = week;
        }

        public static class TotalBean {
            /**
             * name : 豪仔
             * pic : /uploads/imgs/2016/03-25/83664b1eff3400703acbe26b0a5590fa.jpg
             * points : 3380
             * rank : 1
             * richlevel : 8
             * rnum : 30092136
             * sex : 1
             * uid : 2488a7d9785852e085b95b8fccdaae1d
             */

            private String name;
            private String pic;
            private String points;
            private String rank;
            private String richlevel;
            private String rnum;
            private String sex;
            private String uid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getRichlevel() {
                return richlevel;
            }

            public void setRichlevel(String richlevel) {
                this.richlevel = richlevel;
            }

            public String getRnum() {
                return rnum;
            }

            public void setRnum(String rnum) {
                this.rnum = rnum;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }

        public static class WeekBean {
            /**
             * name : 读秀图书
             * pic : /uploads/imgs/2016/11-26/1480138314d6kwv.jpg
             * points : 50
             * rank : 1
             * richlevel : 0
             * rnum : 31296516
             * sex : 1
             * uid : 75f1add00410d815b71adbba82d45de5
             */

            private String name;
            private String pic;
            private String points;
            private String rank;
            private String richlevel;
            private String rnum;
            private String sex;
            private String uid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getRichlevel() {
                return richlevel;
            }

            public void setRichlevel(String richlevel) {
                this.richlevel = richlevel;
            }

            public String getRnum() {
                return rnum;
            }

            public void setRnum(String rnum) {
                this.rnum = rnum;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }
    }
}
