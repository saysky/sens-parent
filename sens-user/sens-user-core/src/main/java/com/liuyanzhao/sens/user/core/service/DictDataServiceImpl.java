package com.liuyanzhao.sens.user.core.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.constant.CommonConstant;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.DictData;
import com.liuyanzhao.sens.user.api.service.DictDataService;
import com.liuyanzhao.sens.user.core.mapper.DictDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-25 17:39
 */
@RestController
@Slf4j
public class DictDataServiceImpl implements DictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public DictData findById(String id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
        return dictDataMapper.selectCount(null);
    }

    @Override
    public DictData insert(DictData entity) {
         dictDataMapper.insert(entity);
         return entity;
    }

    @Override
    public DictData update(DictData entity) {
        dictDataMapper.updateById(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        dictDataMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<String> ids) {
        dictDataMapper.deleteBatchIds(ids);
    }

    @Override
    public Page<DictData> findByCondition(QueryCondition<DictData> queryCondition) {
        DictData dictData = queryCondition.getData();
        Page page = queryCondition.getPage();
        SearchVo searchVo = queryCondition.getSearchVo();

        //对指定字段查询
        QueryWrapper<DictData> queryWrapper = new QueryWrapper<>();
        if (dictData != null) {
            if (dictData.getTitle() != null) {
                queryWrapper.like("title", dictData.getTitle());
            }
            if (dictData.getStatus() != null) {
                queryWrapper.eq("status", dictData.getStatus());
            }
            if (dictData.getDictId() != null) {
                queryWrapper.eq("dictId", dictData.getTitle());
            }
        }

        //查询日期范围
        if (searchVo != null) {
            String startDate = searchVo.getStartDate();
            String endDate = searchVo.getEndDate();
            if (startDate != null && endDate != null) {
                Date start = DateUtil.parse(startDate);
                Date end = DateUtil.parse(endDate);
                queryWrapper.between("create_time", start, end);
            }
        }
        Page<DictData> dictDataPage = (Page<DictData>) dictDataMapper.selectPage(page, queryWrapper);
        return dictDataPage;
    }

    @Override
    public List<DictData> findByDictId(String dictId) {
        return dictDataMapper.findByDictIdAndStatusOrderBySortOrder(dictId, CommonConstant.STATUS_NORMAL);
    }

    @Override
    public void deleteByDictId(String dictId) {
        dictDataMapper.deleteByDictId(dictId);
    }
}
