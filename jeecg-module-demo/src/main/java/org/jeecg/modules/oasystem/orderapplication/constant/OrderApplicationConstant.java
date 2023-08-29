package org.jeecg.modules.oasystem.orderapplication.constant;

public interface OrderApplicationConstant {

    public static final String APPLICANT_NOT_SUBMITTED = "申请人尚未提交";
    public static final String APPLICANT_SUBMITTED_WATING_FOR_MANAGER = "申请人已提交，等待部门主管审核";
    public static final String MANAGER_CONFIRMED_ALL = "部门主管已确认，同意所有物资申请";
    public static final String MANAGER_CONFIRMED_PART = "部门主管已确认，同意部分物资申请";
    public static final String MANAGER_CONFIRMED_NONE = "部门主管已确认，不同意所有物资申请";
    public static final String MANAGER_CONFIRMED_WAITING_FOR_LEADER = "部门主管已确认，等待分管领导审核";
    public static final String LEADER_CONFIRMED_ALL = "分管领导已确认，同意所有物资申请";
    public static final String LEADER_CONFIRMED_PART = "分管领导已确认，同意部分物资申请";
    public static final String LEADER_CONFIRMED_NONE = "分管领导已确认，不同意所有物资申请";
    public static final String APPLICANT_REVOKED = "申请人撤销申请";

}
