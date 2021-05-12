package DO;

import java.util.Date;

/**
 * User: nieshichao
 * Date: 2021/5/3
 */
public class OperatorAction {
    // {"mailNo": "123", "actionTime": "2021-05-04 10:24:10", "actionCode": 400}
    // {"mailNo": "123", "actionTime": "2021-05-04 10:24:15", "actionCode": 500}
    // {"mailNo": "123", "actionTime": "2021-05-04 10:23:00", "actionCode": 500}
    // {"mailNo": "123", "actionTime": "2021-05-04 10:26:00", "actionCode": 400}
    private String mailNo;
    private Date actionTime;
    private Integer actionCode;
    private String siteId;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public String toString() {
        return "OperatorAction{" +
                "mailNo='" + mailNo + '\'' +
                ", actionTime=" + actionTime +
                ", actionCode=" + actionCode +
                '}';
    }
}
