package DO;

import java.util.Date;

/**
 * User: nieshichao
 * Date: 2021/5/3
 */
public class SitePackageTable {

    /**
     * 运单号
      */
    private String mailNo;

    /**
     * 站点ID
     */
    private String siteId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 到站时间
     */
    private Date arrivalTime;

    /**
     * 小件员ID
     */
    private String postmanId;

    /**
     * 小件员名称
     */
    private String postmanName;

    /**
     * 领件时间
     */
    private Date pickupTime;

    /**
     * 反馈时间
     */
    private Date feedbackTime;

    /**
     * 客户名称
     */
    private String customName;

    /**
     * 客户手机
     */
    private String customPhone;

    /**
     * 客户地址
     */
    private String customAddress;
}
