package org.jeecg.modules.oasystem.orderapplication.constant;

public interface OrderApplicationConstant {

    public static final String APPLICANT_NOT_SUBMITTED = "applicant_not_submitted";
    public static final String APPLICANT_SUBMITTED = "applicant_submitted";
    public static final String MANAGER_CONFIRMED_ALL = "manager_confirmed_all";
    public static final String MANAGER_CONFIRMED_PART = "manager_confirmed_part";
    public static final String MANAGER_CONFIRMED_NONE = "manager_confirmed_none";
    public static final String MANAGER_CONFIRMED = "manager_confirmed";
    public static final String LEADER_CONFIRMED_ALL = "leader_confirmed_all";
    public static final String LEADER_CONFIRMED_PART = "leader_confirmed_part";
    public static final String LEADER_CONFIRMED_NONE = "leader_confirmed_none";
    public static final String APPLICANT_REVOKED = "applicant_revoked";

    // 审核人类型，分为部门主管和分管领导
    public static final String AUDITOR_TYPE_MANAGER = "auditor_type_manager";
    public static final String AUDITOR_TYPE_LEADER = "auditor_type_leader";

    // 同意\不同意\未审批
    public static final String DISAGREE = "不同意";
    public static final String AGREE = "同意";
    public static final String NOTDECIDED = "未审核";
}
