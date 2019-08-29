package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.Setting;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 言曌
 * @date 2019-08-25 12:30
 */

@CacheConfig(cacheNames = "setting")
@FeignClient(name = "sens-user-core")
public interface SettingService {

    /**
     * 通过id获取
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    @GetMapping("/setting/findById")
    Setting findById(@RequestParam("id") String id);

    /**
     * 添加
     * @param setting
     * @return
     */
    @CacheEvict(key = "#setting.id")
    @PostMapping("/setting/insert")
    Setting insert(@RequestBody Setting setting);

    /**
     * 修改
     * @param setting
     * @return
     */
    @CacheEvict(key = "#setting.id")
    @PostMapping("/setting/update")
    Setting update(@RequestBody Setting setting);

    /**
     * 添加或修改
     * @param setting
     * @return
     */
    @CacheEvict(key = "#setting.id")
    @PostMapping("/setting/saveOrUpdate")
    Setting saveOrUpdate(@RequestBody Setting setting);
}
