package com.liuyanzhao.sens.user.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.util.PageUtil;
import com.liuyanzhao.sens.common.vo.PageVo;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.api.entity.Dict;
import com.liuyanzhao.sens.user.api.entity.DictData;
import com.liuyanzhao.sens.user.api.service.DictDataService;
import com.liuyanzhao.sens.user.api.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 字典数据管理接口
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/dictData")
@CacheConfig(cacheNames = "dictData")
@Transactional
public class DictDataController{

    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 多条件分页获取用户列表
     * @param dictData
     * @param pageVo
     * @return
     */
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    public Response<Page<DictData>> getByCondition(@ModelAttribute DictData dictData,
                                                   @ModelAttribute PageVo pageVo){

        QueryCondition queryCondition = new QueryCondition(dictData, null, PageUtil.initMpPage(pageVo));
        Page<DictData> page = dictDataService.findByCondition(queryCondition);
        return Response.yes(page);
    }

    /**
     * 通过类型获取
     * @param type
     * @return
     */
    @RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
    @Cacheable(key = "#type")
    public Response<Object> getByType(@PathVariable String type){

        Dict dict = dictService.findByType(type);
        if (dict == null) {
            return Response.no("字典类型Type不存在");
        }
        List<DictData> list = dictDataService.findByDictId(dict.getId());
        return Response.yes(list);
    }

    /**
     * 添加
     * @param dictData
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response<Object> add(@ModelAttribute DictData dictData){

        Dict dict = dictService.findById(dictData.getDictId());
        if (dict == null) {
            return Response.no("字典类型id不存在");
        }
        dictDataService.insert(dictData);
        // 删除缓存
        redisTemplate.delete("dictData::"+dict.getType());
        return Response.yes("添加成功");
    }

    /**
     * 编辑
     * @param dictData
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response<Object> edit(@ModelAttribute DictData dictData){

        dictDataService.update(dictData);
        // 删除缓存
        Dict dict = dictService.findById(dictData.getDictId());
        redisTemplate.delete("dictData::"+dict.getType());
        return Response.yes("编辑成功");
    }

    /**
     * 批量通过id删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    public Response<Object> delByIds(@PathVariable String[] ids){

        for(String id : ids){
            DictData dictData = dictDataService.findById(id);
            Dict dict = dictService.findById(dictData.getDictId());
            dictDataService.deleteById(id);
            // 删除缓存
            redisTemplate.delete("dictData::"+dict.getType());
        }
        return Response.yes("批量通过id删除数据成功");
    }
}
