package org.jeecg.modules.oasystem.orderapplication.rule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationMain;

public class UserVisibilityRule {

    public static QueryWrapper<OrderApplicationMain> viewListRule(QueryWrapper<OrderApplicationMain> queryWrapper) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(!loginUser.getUsername().equals("admin")) {
            queryWrapper.eq("create_by", loginUser.getRealname());
        }
        return queryWrapper;
    }
}
