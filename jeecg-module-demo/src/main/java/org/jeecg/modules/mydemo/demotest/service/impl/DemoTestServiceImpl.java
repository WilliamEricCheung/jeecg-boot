package org.jeecg.modules.mydemo.demotest.service.impl;

import org.jeecg.modules.mydemo.demotest.entity.DemoTest;
import org.jeecg.modules.mydemo.demotest.mapper.DemoTestMapper;
import org.jeecg.modules.mydemo.demotest.service.IDemoTestService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 测试
 * @Author: jeecg-boot
 * @Date:   2023-08-20
 * @Version: V1.0
 */
@Service
public class DemoTestServiceImpl extends ServiceImpl<DemoTestMapper, DemoTest> implements IDemoTestService {

}
