package com.liuyanzhao.sens.user.core.service;

import com.liuyanzhao.sens.user.api.entity.Setting;
import com.liuyanzhao.sens.user.api.service.SettingService;
import com.liuyanzhao.sens.user.core.mapper.SettingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 言曌
 * @date 2019-08-25 12:32
 */

@RestController
@Slf4j
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingMapper settingMapper;

    @Override
    public Setting findById(String id) {
        return settingMapper.selectById(id);
    }

    @Override
    public Setting insert(Setting setting) {
        settingMapper.insert(setting);
        return setting;
    }

    @Override
    public Setting update(Setting setting) {
        settingMapper.updateById(setting);
        return setting;
    }

    @Override
    public Setting saveOrUpdate(Setting setting) {
        String id = setting.getId();
        if (id != null) {
            //查询该记录是否存在
            Setting row = findById(id);
            if (row != null) {
                settingMapper.updateById(setting);
                return setting;
            }
        }
        settingMapper.insert(setting);
        return setting;
    }
}
