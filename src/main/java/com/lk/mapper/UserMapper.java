package com.lk.mapper;


import com.lk.pojo.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User> {
}
