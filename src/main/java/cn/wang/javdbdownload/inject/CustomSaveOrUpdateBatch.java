package cn.wang.javdbdownload.inject;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Table;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

public class CustomSaveOrUpdateBatch extends AbstractMethod {


    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>insert into %s %s values %s ON DUPLICATE KEY UPDATE %s</script>";
        String tableName = tableInfo.getTableName();
        String fieldSql = generateFieldSql(tableInfo);
        String modelValueSql = prepareModelValueSql(tableInfo);
        String duplicateKeySql = prepareDuplicateKeySql(tableInfo);
        String sqlResult = String.format(sql, tableName, fieldSql, modelValueSql, duplicateKeySql);

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, "customSaveOrUpdateBatch", sqlSource, new NoKeyGenerator(), null, null);
    }

    private String prepareDuplicateKeySql(TableInfo tableInfo){
        StringBuilder sb = new StringBuilder();
        if(StrUtil.isNotEmpty(tableInfo.getKeyColumn()) && !tableInfo.getIdType().equals(IdType.AUTO)) {
            sb.append(tableInfo.getKeyColumn()).append("=values(").append(tableInfo.getKeyColumn()).append("),");
        }

        tableInfo.getFieldList().forEach(field -> {
            sb.append(field.getColumn())
                    .append("=values(")
                    .append(field.getColumn())
                    .append("),");
        });
        sb.delete(sb.length() - 1 ,sb.length());

        return sb.toString();
    }

    private String prepareModelValueSql(TableInfo tableInfo){

        StringBuilder sb = new StringBuilder();
        //<foreach collection="list" item="item" index="index" open="(" separator="),(" close=")">
        sb.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">\n");

        if (!tableInfo.getIdType().equals(IdType.AUTO)) {
            sb.append("#{item.").append(tableInfo.getKeyProperty()).append("},");
        }

        tableInfo.getFieldList().forEach(field -> {
            sb.append("#{item.").append(field.getProperty()).append("},");
        });
        sb.delete(sb.length() - 1,sb.length());
        sb.append("\n</foreach>");

        return sb.toString();
    }

    private String generateFieldSql(TableInfo tableInfo){
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        List<String> colList = fieldList.stream().map(TableFieldInfo::getColumn).collect(Collectors.toList());
        if (!tableInfo.getIdType().equals(IdType.AUTO)) {
            colList.add(0,tableInfo.getKeyColumn());
        }
        return String.format("(%s) ",String.join(",",colList));
    }

}
