package com.lhcz.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 41008
 */
public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
