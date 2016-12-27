

package com.khzpds.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@SuppressWarnings("serial")
public class UserCompletionItemApplyInfo implements Serializable
{
	
	private String id;   
	private String userId;   
	private String userName;   
	private String activityId;   
	private String competitionItemId;   
	private String competitionType;   
	private java.util.Date createTime;   
	private String applyStatus;   
	private String productionName;   
	private String applyGroup;   
	private String applyYearGroup;   
	private String realName;   
	private java.util.Date birthday;   
	private String sex;   
	private String schoolName;   
	private String classCompany;   
	private String recommenedCompany;   
	private String cardType;   
	private String cardNumber;   
	private String telephone;   
	private String mobilePhone;   
	private String email;   
	private String postcode;   
	private String address;   
	private String ideaDesc;   
	private String fileName;   
	private String filePath;   
	private String scriptwriter;   
	private String director;   
	private String camerist;   
	private String editor;   
	private String musician;   
	private String artist;   
	private String mainStuff;   
	private String teamDesc;   
	private String approveStatus;   
	private String approveUserId;   
	private String approveUserName;   
	private String approveType;   
	private Double reviewPoint;   
	private String vdef1;   
	private String vdef2;   
	private String vdef3;   
    
    public UserCompletionItemApplyInfo(){}
   
    
    /**
     * 
     */ 	
	public String getId(){
        return id;
    }
    
     /**
     * 
     */ 	
    public void setId(String id){
        this.id=id;
    }
    
    /**
     * 
     */ 	
	public String getUserId(){
        return userId;
    }
    
     /**
     * 
     */ 	
    public void setUserId(String userId){
        this.userId=userId;
    }
    
    /**
     * 
     */ 	
	public String getUserName(){
        return userName;
    }
    
     /**
     * 
     */ 	
    public void setUserName(String userName){
        this.userName=userName;
    }
    
    /**
     * 活动id
     */ 	
	public String getActivityId(){
        return activityId;
    }
    
     /**
     * 活动id
     */ 	
    public void setActivityId(String activityId){
        this.activityId=activityId;
    }
    
    /**
     * 比赛项目id
     */ 	
	public String getCompetitionItemId(){
        return competitionItemId;
    }
    
     /**
     * 比赛项目id
     */ 	
    public void setCompetitionItemId(String competitionItemId){
        this.competitionItemId=competitionItemId;
    }
    
    /**
     * 比赛类型 冗余
     */ 	
	public String getCompetitionType(){
        return competitionType;
    }
    
     /**
     * 比赛类型 冗余
     */ 	
    public void setCompetitionType(String competitionType){
        this.competitionType=competitionType;
    }
    
    /**
     * 提交时间
     */ 	
	public java.util.Date getCreateTime(){
        return createTime;
    }
    
     /**
     * 提交时间
     */ 	
    public void setCreateTime(java.util.Date createTime){
        this.createTime=createTime;
    }
    
    /**
     * 用户报名状态 字典302
     */ 	
	public String getApplyStatus(){
        return applyStatus;
    }
    
     /**
     * 用户报名状态 字典302
     */ 	
    public void setApplyStatus(String applyStatus){
        this.applyStatus=applyStatus;
    }
    
    /**
     * 作品名称
     */ 	
	public String getProductionName(){
        return productionName;
    }
    
     /**
     * 作品名称
     */ 	
    public void setProductionName(String productionName){
        this.productionName=productionName;
    }
    
    /**
     * 参赛组别 科幻小说303 科幻画305
     */ 	
	public String getApplyGroup(){
        return applyGroup;
    }
    
     /**
     * 参赛组别 科幻小说303 科幻画305
     */ 	
    public void setApplyGroup(String applyGroup){
        this.applyGroup=applyGroup;
    }
    
    /**
     * 参赛年龄组 小说306 画304 视频307
     */ 	
	public String getApplyYearGroup(){
        return applyYearGroup;
    }
    
     /**
     * 参赛年龄组 小说306 画304 视频307
     */ 	
    public void setApplyYearGroup(String applyYearGroup){
        this.applyYearGroup=applyYearGroup;
    }
    
    /**
     * 报名真实姓名
     */ 	
	public String getRealName(){
        return realName;
    }
    
     /**
     * 报名真实姓名
     */ 	
    public void setRealName(String realName){
        this.realName=realName;
    }
    
    /**
     * 出生年月
     */ 	
	public java.util.Date getBirthday(){
        return birthday;
    }
    
     /**
     * 出生年月
     */ 	
    public void setBirthday(java.util.Date birthday){
        this.birthday=birthday;
    }
    
    /**
     * 性别
     */ 	
	public String getSex(){
        return sex;
    }
    
     /**
     * 性别
     */ 	
    public void setSex(String sex){
        this.sex=sex;
    }
    
    /**
     * 所在学校名称
     */ 	
	public String getSchoolName(){
        return schoolName;
    }
    
     /**
     * 所在学校名称
     */ 	
    public void setSchoolName(String schoolName){
        this.schoolName=schoolName;
    }
    
    /**
     * 年级单位
     */ 	
	public String getClassCompany(){
        return classCompany;
    }
    
     /**
     * 年级单位
     */ 	
    public void setClassCompany(String classCompany){
        this.classCompany=classCompany;
    }
    
    /**
     * 推荐单位
     */ 	
	public String getRecommenedCompany(){
        return recommenedCompany;
    }
    
     /**
     * 推荐单位
     */ 	
    public void setRecommenedCompany(String recommenedCompany){
        this.recommenedCompany=recommenedCompany;
    }
    
    /**
     * 证件类型
     */ 	
	public String getCardType(){
        return cardType;
    }
    
     /**
     * 证件类型
     */ 	
    public void setCardType(String cardType){
        this.cardType=cardType;
    }
    
    /**
     * 证件号
     */ 	
	public String getCardNumber(){
        return cardNumber;
    }
    
     /**
     * 证件号
     */ 	
    public void setCardNumber(String cardNumber){
        this.cardNumber=cardNumber;
    }
    
    /**
     * 电话
     */ 	
	public String getTelephone(){
        return telephone;
    }
    
     /**
     * 电话
     */ 	
    public void setTelephone(String telephone){
        this.telephone=telephone;
    }
    
    /**
     * 手机号
     */ 	
	public String getMobilePhone(){
        return mobilePhone;
    }
    
     /**
     * 手机号
     */ 	
    public void setMobilePhone(String mobilePhone){
        this.mobilePhone=mobilePhone;
    }
    
    /**
     * 邮箱
     */ 	
	public String getEmail(){
        return email;
    }
    
     /**
     * 邮箱
     */ 	
    public void setEmail(String email){
        this.email=email;
    }
    
    /**
     * 邮编
     */ 	
	public String getPostcode(){
        return postcode;
    }
    
     /**
     * 邮编
     */ 	
    public void setPostcode(String postcode){
        this.postcode=postcode;
    }
    
    /**
     * 通讯地址
     */ 	
	public String getAddress(){
        return address;
    }
    
     /**
     * 通讯地址
     */ 	
    public void setAddress(String address){
        this.address=address;
    }
    
    /**
     * 创意说明
     */ 	
	public String getIdeaDesc(){
        return ideaDesc;
    }
    
     /**
     * 创意说明
     */ 	
    public void setIdeaDesc(String ideaDesc){
        this.ideaDesc=ideaDesc;
    }
    
    /**
     * 
     */ 	
	public String getFileName(){
        return fileName;
    }
    
     /**
     * 
     */ 	
    public void setFileName(String fileName){
        this.fileName=fileName;
    }
    
    /**
     * 上传文件位置
     */ 	
	public String getFilePath(){
        return filePath;
    }
    
     /**
     * 上传文件位置
     */ 	
    public void setFilePath(String filePath){
        this.filePath=filePath;
    }
    
    /**
     * 编剧
     */ 	
	public String getScriptwriter(){
        return scriptwriter;
    }
    
     /**
     * 编剧
     */ 	
    public void setScriptwriter(String scriptwriter){
        this.scriptwriter=scriptwriter;
    }
    
    /**
     * 导演
     */ 	
	public String getDirector(){
        return director;
    }
    
     /**
     * 导演
     */ 	
    public void setDirector(String director){
        this.director=director;
    }
    
    /**
     * 摄影师
     */ 	
	public String getCamerist(){
        return camerist;
    }
    
     /**
     * 摄影师
     */ 	
    public void setCamerist(String camerist){
        this.camerist=camerist;
    }
    
    /**
     * 剪辑
     */ 	
	public String getEditor(){
        return editor;
    }
    
     /**
     * 剪辑
     */ 	
    public void setEditor(String editor){
        this.editor=editor;
    }
    
    /**
     * 音乐
     */ 	
	public String getMusician(){
        return musician;
    }
    
     /**
     * 音乐
     */ 	
    public void setMusician(String musician){
        this.musician=musician;
    }
    
    /**
     * 美术
     */ 	
	public String getArtist(){
        return artist;
    }
    
     /**
     * 美术
     */ 	
    public void setArtist(String artist){
        this.artist=artist;
    }
    
    /**
     * 主演
     */ 	
	public String getMainStuff(){
        return mainStuff;
    }
    
     /**
     * 主演
     */ 	
    public void setMainStuff(String mainStuff){
        this.mainStuff=mainStuff;
    }
    
    /**
     * 参数团队简介
     */ 	
	public String getTeamDesc(){
        return teamDesc;
    }
    
     /**
     * 参数团队简介
     */ 	
    public void setTeamDesc(String teamDesc){
        this.teamDesc=teamDesc;
    }
    
    /**
     * 审批状态 -1 未审核 0不通过 1通过
     */ 	
	public String getApproveStatus(){
        return approveStatus;
    }
    
     /**
     * 审批状态 -1 未审核 0不通过 1通过
     */ 	
    public void setApproveStatus(String approveStatus){
        this.approveStatus=approveStatus;
    }
    
    /**
     * 审批人id
     */ 	
	public String getApproveUserId(){
        return approveUserId;
    }
    
     /**
     * 审批人id
     */ 	
    public void setApproveUserId(String approveUserId){
        this.approveUserId=approveUserId;
    }
    
    /**
     * 审批人用户名
     */ 	
	public String getApproveUserName(){
        return approveUserName;
    }
    
     /**
     * 审批人用户名
     */ 	
    public void setApproveUserName(String approveUserName){
        this.approveUserName=approveUserName;
    }
    
    /**
     * 审批类型  专家or组织机构
     */ 	
	public String getApproveType(){
        return approveType;
    }
    
     /**
     * 审批类型  专家or组织机构
     */ 	
    public void setApproveType(String approveType){
        this.approveType=approveType;
    }
    
    /**
     * 专家复赛评分
     */ 	
	public Double getReviewPoint(){
        return reviewPoint;
    }
    
     /**
     * 专家复赛评分
     */ 	
    public void setReviewPoint(Double reviewPoint){
        this.reviewPoint=reviewPoint;
    }
    
    /**
     * 
     */ 	
	public String getVdef1(){
        return vdef1;
    }
    
     /**
     * 
     */ 	
    public void setVdef1(String vdef1){
        this.vdef1=vdef1;
    }
    
    /**
     * 
     */ 	
	public String getVdef2(){
        return vdef2;
    }
    
     /**
     * 
     */ 	
    public void setVdef2(String vdef2){
        this.vdef2=vdef2;
    }
    
    /**
     * 
     */ 	
	public String getVdef3(){
        return vdef3;
    }
    
     /**
     * 
     */ 	
    public void setVdef3(String vdef3){
        this.vdef3=vdef3;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("UserId",getUserId())
           .append("UserName",getUserName())
           .append("ActivityId",getActivityId())
           .append("CompetitionItemId",getCompetitionItemId())
           .append("CompetitionType",getCompetitionType())
           .append("CreateTime",getCreateTime())
           .append("ApplyStatus",getApplyStatus())
           .append("ProductionName",getProductionName())
           .append("ApplyGroup",getApplyGroup())
           .append("ApplyYearGroup",getApplyYearGroup())
           .append("RealName",getRealName())
           .append("Birthday",getBirthday())
           .append("Sex",getSex())
           .append("SchoolName",getSchoolName())
           .append("ClassCompany",getClassCompany())
           .append("RecommenedCompany",getRecommenedCompany())
           .append("CardType",getCardType())
           .append("CardNumber",getCardNumber())
           .append("Telephone",getTelephone())
           .append("MobilePhone",getMobilePhone())
           .append("Email",getEmail())
           .append("Postcode",getPostcode())
           .append("Address",getAddress())
           .append("IdeaDesc",getIdeaDesc())
           .append("FileName",getFileName())
           .append("FilePath",getFilePath())
           .append("Scriptwriter",getScriptwriter())
           .append("Director",getDirector())
           .append("Camerist",getCamerist())
           .append("Editor",getEditor())
           .append("Musician",getMusician())
           .append("Artist",getArtist())
           .append("MainStuff",getMainStuff())
           .append("TeamDesc",getTeamDesc())
           .append("ApproveStatus",getApproveStatus())
           .append("ApproveUserId",getApproveUserId())
           .append("ApproveUserName",getApproveUserName())
           .append("ApproveType",getApproveType())
           .append("ReviewPoint",getReviewPoint())
           .append("Vdef1",getVdef1())
           .append("Vdef2",getVdef2())
           .append("Vdef3",getVdef3())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserCompletionItemApplyInfo == false) return false;
		if(this == obj) return true;
		UserCompletionItemApplyInfo other = (UserCompletionItemApplyInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    //--CustomBegin***///
	private String recommenedCompanyName;

	public String getRecommenedCompanyName() {
		return recommenedCompanyName;
	}


	public void setRecommenedCompanyName(String recommenedCompanyName) {
		this.recommenedCompanyName = recommenedCompanyName;
	}
	
	//--CustomEnd*****///
}

