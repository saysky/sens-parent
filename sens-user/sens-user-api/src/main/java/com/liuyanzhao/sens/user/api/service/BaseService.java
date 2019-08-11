//package com.liuyanzhao.sens.user.api.service;
//
//import com.baomidou.mybatisplus.mapper.BaseMapper;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.baomidou.mybatisplus.plugins.Page;
//
//import java.io.Serializable;
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//
///**
// * @author 言曌
// * @date 2019-08-11 10:53
// */
//
//// JDK8函数式接口注解 仅能包含一个抽象方法
//@FunctionalInterface
//public interface BaseService<E, ID extends Serializable> {
//
//    BaseMapper<E> baseMapper();
//
//    /**
//     * 删除标准
//     */
//    Integer DEL_FLAG = 1;
//
//    /**
//     * 根据ID获取
//     *
//     * @param id
//     * @return
//     */
//    default E get(ID id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        E e = baseMapper().selectById(id);
//        if (e.getClass().getMethod("getDelFlag").invoke(e).toString() == "1") {
//            return null;
//        }
//        return e;
//    }
//
//    /**
//     * 获取所有列表
//     *
//     * @return
//     */
//    default List<E> getAll() {
//        return baseMapper().selectList(
//                new EntityWrapper<E>().eq("del_flag", "0"));
//    }
//
//    /**
//     * 分页获取所有列表
//     *
//     * @return
//     */
//    default List<E> pageAll() {
//        return baseMapper().selectPage(
//                new Page<E>(1, 10),
//                new EntityWrapper<E>().eq("del_flag", "0")
//        );
//    }
//
//    /**
//     * 获取总数
//     *
//     * @return
//     */
//    default Integer getTotalCount() {
//        return baseMapper().selectCount(null);
//    }
//
//    /**
//     * 保存
//     *
//     * @param entity
//     * @return
//     */
//    default Integer save(E entity) {
//        return baseMapper().insert(entity);
//    }
//
//    /**
//     * 修改
//     *
//     * @param entity
//     * @return
//     */
//    default Integer update(E entity) {
//        return baseMapper().updateById(entity);
//    }
//
//    /**
//     * 批量保存与修改
//     *
//     * @param list
//     * @return
//     */
//    default Integer batchUpdate(List<E> list) {
//        Integer count = 0;
//        for (E e : list) {
//            count += baseMapper().updateById(e);
//        }
//        return count;
//    }
//
//    /**
//     * 删除
//     *
//     * @param id
//     */
//    default void delete(E e) {
//        Wrapper<E> wrapper = new QueryW
//        baseMapper().update(e, )
//    }
//
//
//    /**
//     * 批量删除
//     *
//     * @param ids
//     */
//    default void delete(List<ID> ids) {
//        baseMapper().deleteBatchIds(ids);
//        baseMapper().update(ids);
//    }
//
//    /**
//     * 清空缓存，提交持久化
//     */
//    default void flush() {
//        getRepository().flush();
//    }
//
//    /**
//     * 根据条件查询获取
//     *
//     * @param spec
//     * @return
//     */
//    default List<E> findAll(Specification<E> spec) {
//        return getRepository().findAll(spec);
//    }
//
//    /**
//     * 分页获取
//     *
//     * @param pageable
//     * @return
//     */
//    default Page<E> findAll(Pageable pageable) {
//        return getRepository().findAll(pageable);
//    }
//
//    /**
//     * 根据查询条件分页获取
//     *
//     * @param spec
//     * @param pageable
//     * @return
//     */
//    default Page<E> findAll(Specification<E> spec, Pageable pageable) {
//        return getRepository().findAll(spec, pageable);
//    }
//
//    /**
//     * 获取查询条件的结果数
//     *
//     * @param spec
//     * @return
//     */
//    default long count(Specification<E> spec) {
//        return getRepository().count(spec);
//    }
//}