package org.jeecg.modules.oasystem.orderapplication.constant;

public interface OrderApplicationConstant {

    public static final String APPLICANT_NOT_SUBMITTED = "applicant_not_submitted";
    public static final String APPLICANT_SUBMITTED_WATING_FOR_MANAGER = "applicant_submitted_waiting_for_manager";
    public static final String MANAGER_CONFIRMED_ALL = "manager_confirmed_all";
    public static final String MANAGER_CONFIRMED_PART = "manager_confirmed_part";
    public static final String MANAGER_CONFIRMED_NONE = "manager_confirmed_none";
    public static final String MANAGER_CONFIRMED_WAITING_FOR_LEADER = "manager_confirmed_waiting_for_leader";
    public static final String LEADER_CONFIRMED_ALL = "leader_confirmed_all";
    public static final String LEADER_CONFIRMED_PART = "leader_confirmed_part";
    public static final String LEADER_CONFIRMED_NONE = "leader_confirmed_none";
    public static final String APPLICANT_REVOKED = "applicant_revoked";

    // 消息推送模板code，需要和数据库保持一致
    public static final String MESSAGE_TEMPLATE_CODE = "order_application";

    // 审核人类型，分为部门主管和分管领导
    public static final String AUDITOR_TYPE_MANAGER = "auditor_type_manager";
    public static final String AUDITOR_TYPE_LEADER = "auditor_type_leader";

    // 同意或不同意
    public static final String DISAGREE = "0";
    public static final String AGREE = "1";
}
