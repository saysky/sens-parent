package com.liuyanzhao.sens.user.core.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.Log;
import com.liuyanzhao.sens.user.api.service.LogService;
import com.liuyanzhao.sens.user.core.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @author 言曌
 * @date 2019-08-19 18:07
 */
@RestController
@Slf4j
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;


    @Override
    public Log findById(String id) {
        return logMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
        return logMapper.selectCount(null);
    }

    @Override
    public Log insert(Log log) {
        logMapper.insert(log);
        return log;
    }

    @Override
    public Log update(Log entity) {
        logMapper.updateById(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        logMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<String> ids) {
        logMapper.deleteBatchIds(ids);
    }

    @Override
    public Page<Log> findByCondition(QueryCondition<Log> logCondition) {
        Log log = logCondition.getData();
        Page page = logCondition.getPage();
        SearchVo searchVo = logCondition.getSearchVo();

        //对指定字段查询
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        if (log != null) {
            if (log.getLogType() != null) {
                queryWrapper.eq("type", log.getLogType());
            }
        }

        if (searchVo != null) {
            //根据关键字查询
            if (!Strings.isNullOrEmpty(searchVo.getKeywords())) {
                queryWrapper.like("request_url", log.getRequestUrl())
                        .or().like("request_param", log.getRequestParam())
                        .or().like("request_type", log.getRequestType())
                        .or().like("username", log.getUsername())
                        .or().like("ip", log.getIp())
                        .or().like("ip_info", log.getIpInfo())
                        .or().like("name", log.getName());
            }

            //查询日期范围
            String startDate = searchVo.getStartDate();
            String endDate = searchVo.getEndDate();
            if (startDate != null && endDate != null) {
                Date start = DateUtil.parse(startDate);
                Date end = DateUtil.parse(endDate);
                queryWrapper.between("create_time", start, end);
            }
        }
        Page<Log> logPage = (Page<Log>) logMapper.selectPage(page, queryWrapper);
        return logPage;
    }


    @Override
    public void deleteAll() {
        logMapper.deleteAll();
    }
}
