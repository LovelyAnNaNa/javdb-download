package cn.wang.javdbdownload.inject;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;
import java.util.stream.Collectors;

public class CustomSaveOrUpdateBatch extends AbstractMethod {


    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = generateSql(tableInfo);
        System.out.println(sql);

        return null;
    }


    private String generateSql(TableInfo tableInfo){
        StringBuilder sqlSb = new StringBuilder();
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        List<String> colList = fieldList.stream().map(TableFieldInfo::getColumn).collect(Collectors.toList());
        sqlSb.append(String.format("INSERT INTO %s(%s) VALUES",tableInfo.getTableName(),String.join(",",colList)));

        fieldList.forEach(field -> {

        });


        sqlSb.append("ON DUPLICATE KEY UPDATE");
        return sqlSb.toString();
    }

}
