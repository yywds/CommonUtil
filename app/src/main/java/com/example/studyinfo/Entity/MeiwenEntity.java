package com.example.studyinfo.Entity;

import java.util.List;

public class MeiwenEntity{

    /**
     * result : {"stat":"1","cart":[{"image":"upload/2020/05/30/52p7xffu.jpeg","price":"480","name":"小度","count":"4","id":5,"time":"2020-05-30","title":"鞋子","status":"0"},{"image":"upload/2020/05/30/akmoe2sl.jpg","price":"175","name":"想跑","count":"5","id":6,"time":"2020-05-30","title":"水果","status":"1"},{"image":"upload/2020/05/30/a6o899wf.jpg","price":"178","name":"杨","count":"1","id":8,"time":"2020-05-31","title":"衣服","status":"0"},{"image":"upload/2020/05/30/a6o899wf.jpg","price":"195","name":"杨小文","count":"5","id":10,"time":"2020-05-31","title":"衣服","status":"1"},{"image":"upload/2020/05/30/a6o899wf.jpg","price":"312","name":"杨小文","count":"8","id":11,"time":"2020-05-31","title":"衣服","status":"1"}]}
     * reason : 成功的返回
     */

    private ResultBean result;
    private String reason;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class ResultBean {
        /**
         * stat : 1
         * cart : [{"image":"upload/2020/05/30/52p7xffu.jpeg","price":"480","name":"小度","count":"4","id":5,"time":"2020-05-30","title":"鞋子","status":"0"},{"image":"upload/2020/05/30/akmoe2sl.jpg","price":"175","name":"想跑","count":"5","id":6,"time":"2020-05-30","title":"水果","status":"1"},{"image":"upload/2020/05/30/a6o899wf.jpg","price":"178","name":"杨","count":"1","id":8,"time":"2020-05-31","title":"衣服","status":"0"},{"image":"upload/2020/05/30/a6o899wf.jpg","price":"195","name":"杨小文","count":"5","id":10,"time":"2020-05-31","title":"衣服","status":"1"},{"image":"upload/2020/05/30/a6o899wf.jpg","price":"312","name":"杨小文","count":"8","id":11,"time":"2020-05-31","title":"衣服","status":"1"}]
         */

        private String stat;
        private List<CartBean> cart;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<CartBean> getCart() {
            return cart;
        }

        public void setCart(List<CartBean> cart) {
            this.cart = cart;
        }

        public static class CartBean {
            /**
             * image : upload/2020/05/30/52p7xffu.jpeg
             * price : 480
             * name : 小度
             * count : 4
             * id : 5
             * time : 2020-05-30
             * title : 鞋子
             * status : 0
             */

            private String image;
            private String price;
            private String name;
            private String count;
            private int id;
            private String time;
            private String title;
            private String status;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
