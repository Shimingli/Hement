package com.shiming.base.login;


import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *    登录账号的保存
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/14 10:49
 */
public class LoginAccount implements Serializable {

    private static final long serialVersionUID = 2282021318938250513L;
    /**
     * 刷新token
     */
    public String refreshToken;
    /**
     * refreshtoken有效期，时间
     */
    public String refreshTokenExpire;
    /**
     * 认证token
     */
    public String token;
    /**
     * token有效期，时间
     */
    public String tokenExpire;

    /**
     * userId
     */
    public String userId;
    /**
     * 公司id
     */
    public String companyId;
    public String firstName;
    /**
     * 门店的id
     */
    public List<String> shopIds;


}
