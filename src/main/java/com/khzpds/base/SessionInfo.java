package com.khzpds.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SessionInfo implements Serializable{
    private String platFormId;
    private String platFormName;
    private String userId;
    private String userName;
    private String userIdCard;
    private String orgId;
    private String OrgName;
	private String loginDomain;
    private String realName;
    private String platformShortHand;
    private boolean ifGuangXi=false;
    
     
    public boolean getIfGuangXi() {
        return ifGuangXi;
    }
    public void setIfGuangXi(boolean ifGuangXi) {
        this.ifGuangXi = ifGuangXi;
    }
	public String getPlatformShortHand() {
		return platformShortHand;
	}
	public void setPlatformShortHand(String platformShortHand) {
		this.platformShortHand = platformShortHand;
	}
	//学员标志
    private Boolean ifStudent;
   /* private List<RoleInfo> roles;
    private List<UserOrganizationInfo> orgIds;*/
    private String department;
    private String roleType;
    //超级管理员标志
    private Boolean ifAdmin;
    //管理员标志
    private Boolean ifManager;
    private String password;
    
    //zlg添加  本次登录是否发送过考试通知
    private Boolean ifSendExamNotice;
    
    public Boolean getIfSendExamNotice() {
		return ifSendExamNotice;
	}
	public void setIfSendExamNotice(Boolean ifSendExamNotice) {
		this.ifSendExamNotice = ifSendExamNotice;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIfManager() {
		return ifManager;
	}
	public void setIfManager(Boolean ifManager) {
		this.ifManager = ifManager;
	}
	public Boolean getIfAdmin() {
		return ifAdmin;
	}
	public void setIfAdmin(Boolean ifAdmin) {
		this.ifAdmin = ifAdmin;
	}
	public Boolean getIfStudent() {
		return ifStudent;
	}
	public void setIfStudent(Boolean ifStudent) {
		this.ifStudent = ifStudent;
	}
    
    public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	/*public List<UserOrganizationInfo> getOrgIds() {
        return orgIds;
    }
    public void setOrgIds(List<UserOrganizationInfo> orgIds) {
        this.orgIds = orgIds;
    }
    public List<RoleInfo> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleInfo> roles) {
        this.roles = roles;
    }*/
    public String getPlatFormId() {
        return platFormId;
    }
    public void setPlatFormId(String platFormId) {
        this.platFormId = platFormId;
    }
    public String getPlatFormName() {
        return platFormName;
    }
    public void setPlatFormName(String platFormName) {
        this.platFormName = platFormName;
    }
     
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    public String getOrgName() {
        return OrgName;
    }
    public void setOrgName(String orgName) {
        OrgName = orgName;
    }
    
    public String getUserIdCard() {
		return userIdCard;
	}
	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}
	/*
                  登录页链接过来的域名
     */
    public String getLoginDomain() {
        return loginDomain;
    }
    /*
                    登录页链接过来的域名
    */
    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
    
}
