package cn.wang.javdbdownload.util;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.io.File;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * bean属性获取工具类
 */
public class FieldUtil {


    public static String getKeyName(Class clz){
        Field keyFiled = getKeyFiled(clz);
        if (keyFiled != null){
            return keyFiled.getName();
        }

        return null;
    }


    public static Field getKeyFiled(Class clz){
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null){
                return field;
            }
        }

        return null;
    }

    /**
     * 将bean的属性的get方法，作为lambda表达式传入时，获取get方法对应的属性Field
     *
     * @param fn  lambda表达式，bean的属性的get方法
     * @param <T> 泛型
     * @return 属性对象
     */
    public static <T> Field getField(SFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);

        // 从lambda信息取出method、field、class等
        String implMethodName = serializedLambda.getImplMethodName();
		// 确保方法是符合规范的get方法，boolean类型是is开头
        if (!implMethodName.startsWith("is") && !implMethodName.startsWith("get")) {
            throw new RuntimeException("get方法名称: " + implMethodName + ", 不符合java bean规范");
        }

        // get方法开头为 is 或者 get，将方法名 去除is或者get，然后首字母小写，就是属性名
        int prefixLen = implMethodName.startsWith("is") ? 2 : 3;

        String fieldName = implMethodName.substring(prefixLen);
        String firstChar = fieldName.substring(0, 1);
        fieldName = fieldName.replaceFirst(firstChar, firstChar.toLowerCase());
        Field field;
        try {
            field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        return field;
    }
}