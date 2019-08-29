package com.liuyanzhao.sens.user.core.service;

import com.liuyanzhao.sens.user.api.entity.Dict;
import com.liuyanzhao.sens.user.api.service.DictService;
import com.liuyanzhao.sens.user.core.mapper.DictMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-25 17:19
 */
@RestController
@Slf4j
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public Dict findById(String id) {
        return dictMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
        return dictMapper.selectCount(null);
    }

    @Override
    public Dict insert(Dict entity) {
        dictMapper.insert(entity);
        return entity;
    }

    @Override
    public Dict update(Dict entity) {
        dictMapper.updateById(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        dictMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<String> ids) {
        dictMapper.deleteBatchIds(ids);
    }

    @Override
    public List<Dict> findAllOrderBySortOrder() {
        return dictMapper.findAllOrderBySortOrder();
    }

    @Override
    public Dict findByType(String type) {
        return dictMapper.findByType(type);
    }

    @Override
    public List<Dict> findByTitleOrTypeLike(String key) {
        return dictMapper.findByTitleOrTypeLike(key);
    }
}
