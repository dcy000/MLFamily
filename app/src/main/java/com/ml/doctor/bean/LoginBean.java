package com.ml.doctor.bean;

/**
 * Created by gzq on 2017/11/21.
 * 登陆成功返回的字段
 */

public class LoginBean {
    private int docterid, state, priority, amount, pend, number, evaluation, apply_amount,
            service_amount;
    private String doctername, tel, adds,
            duty, department, documents,
            card, gat, pro, tj,docter_photo;
    private String hosname;
    private R r;

    public int getDocterid() {
        return docterid;
    }

    public void setDocterid(int docterid) {
        this.docterid = docterid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPend() {
        return pend;
    }

    public void setPend(int pend) {
        this.pend = pend;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getApply_amount() {
        return apply_amount;
    }

    public void setApply_amount(int apply_amount) {
        this.apply_amount = apply_amount;
    }

    public int getService_amount() {
        return service_amount;
    }

    public void setService_amount(int service_amount) {
        this.service_amount = service_amount;
    }

    public String getDoctername() {
        return doctername;
    }

    public void setDoctername(String doctername) {
        this.doctername = doctername;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdds() {
        return adds;
    }

    public void setAdds(String adds) {
        this.adds = adds;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getGat() {
        return gat;
    }

    public void setGat(String gat) {
        this.gat = gat;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getTj() {
        return tj;
    }

    public void setTj(String tj) {
        this.tj = tj;
    }

    public String getDocter_photo() {
        return docter_photo;
    }

    public void setDocter_photo(String docter_photo) {
        this.docter_photo = docter_photo;
    }

    public String getHosname() {
        return hosname;
    }

    public void setHosname(String hosname) {
        this.hosname = hosname;
    }

    public R getR() {
        return r;
    }

    public void setR(R r) {
        this.r = r;
    }

    public class R{
        private int rankid;
        private String rankname;

        public int getRankid() {
            return rankid;
        }

        public void setRankid(int rankid) {
            this.rankid = rankid;
        }

        public String getRankname() {
            return rankname;
        }

        public void setRankname(String rankname) {
            this.rankname = rankname;
        }

        @Override
        public String toString() {
            return "R{" +
                    "rankid='" + rankid + '\'' +
                    ", rankname='" + rankname + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "docterid=" + docterid +
                ", state=" + state +
                ", priority=" + priority +
                ", amount=" + amount +
                ", pend=" + pend +
                ", number=" + number +
                ", evaluation=" + evaluation +
                ", apply_amount=" + apply_amount +
                ", service_amount=" + service_amount +
                ", doctername='" + doctername + '\'' +
                ", tel='" + tel + '\'' +
                ", adds='" + adds + '\'' +
                ", duty='" + duty + '\'' +
                ", department='" + department + '\'' +
                ", documents='" + documents + '\'' +
                ", card='" + card + '\'' +
                ", gat='" + gat + '\'' +
                ", pro='" + pro + '\'' +
                ", tj='" + tj + '\'' +
                ", docter_photo='" + docter_photo + '\'' +
                ", hosname='" + hosname + '\'' +
                ", r=" + r +
                '}';
    }
}
