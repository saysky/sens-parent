package com.liuyanzhao.sens.user.web.controller;

import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.api.entity.Dict;
import com.liuyanzhao.sens.user.api.service.DictDataService;
import com.liuyanzhao.sens.user.api.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 字典管理接口
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/dict")
@Transactional
public class DictController{

    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取全部数据
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Response<List<Dict>> getAll(){

        List<Dict> list = dictService.findAllOrderBySortOrder();
        return Response.yes(list);
    }

    /**
     * 添加
     * @param dict
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response<Object> add(@ModelAttribute Dict dict){

        if(dictService.findByType(dict.getType())!=null){
            return Response.yes("字典类型Type已存在");
        }
        dictService.insert(dict);
        return Response.yes("添加成功");
    }

    /**
     * 编辑
     * @param dict
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response<Object> edit(@ModelAttribute Dict dict){

        Dict old = dictService.findById(dict.getId());
        // 若type修改判断唯一
        if(!old.getType().equals(dict.getType())&&dictService.findByType(dict.getType())!=null){
            return Response.yes("字典类型Type已存在");
        }
        dictService.update(dict);
        return Response.yes("编辑成功");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delByIds/{id}", method = RequestMethod.DELETE)
    public Response<Object> delAllByIds(@PathVariable String id){


        Dict dict = dictService.findById(id);
        dictService.deleteById(id);
        dictDataService.deleteByDictId(id);
        // 删除缓存
        redisTemplate.delete("dictData::"+dict.getType());
        return Response.yes("删除成功");
    }


    /**
     * 搜索字典
     * @param key
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Response<List<Dict>> searchPermissionList(@RequestParam String key){

        List<Dict> list = dictService.findByTitleOrTypeLike(key);
        return Response.yes(list);
    }
}
