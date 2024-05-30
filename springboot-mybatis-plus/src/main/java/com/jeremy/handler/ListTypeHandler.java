package com.jeremy.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laizc
 * @description list类型处理器
 * @date 2023/12/15 13:51
 */
@Slf4j
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public abstract class ListTypeHandler<T> extends BaseTypeHandler<List<T>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<T> ts, JdbcType jdbcType) throws SQLException {
        String content = CollectionUtil.isEmpty(ts) ? null : JSON.toJSONString(ts);
        preparedStatement.setString(i, content);
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        try {
            return this.getListByJsonArrayString(resultSet.getString(s));
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        try {
            return this.getListByJsonArrayString(resultSet.getString(i));
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        try {
            return this.getListByJsonArrayString(callableStatement.getString(i));
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    private List<T> getListByJsonArrayString(String content) throws IOException {
        return StringUtils.isEmpty(content) ? new ArrayList<>() : new ObjectMapper().readValue(content, this.specificType());
    }

    protected abstract TypeReference<List<T>> specificType();

}
