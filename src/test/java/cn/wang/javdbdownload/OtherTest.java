package cn.wang.javdbdownload;

import cn.hutool.core.lang.func.Func1;
import cn.wang.javdbdownload.jm.common.JmConstants;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.function.Consumer;

import static cn.wang.javdbdownload.jm.common.JmConstants.ALBUM_PIC_BASE_PATH;
import static cn.wang.javdbdownload.jm.common.JmConstants.PIC_BASE_PATH;

public class OtherTest {


    public void testLambda(Album album, Consumer column){

        String str = "不可视汉化 (ショタスクラッチ12) HEATWAVEsupernova (快刀雄飛雪町灯之助) ヒデヨシディレクターズカット (バカとテストと召喚獣) 中国翻訳_23792";
        String stb = "不可视汉化 (ショタスクラッチ12) HEATWAVEsuper:nova (快刀雄飛雪町灯之助) ヒデヨシディレクターズカット (バカとテストと召喚獣) 中国翻訳_23792";
    }

    @Test
    public void test11() throws Exception{


        System.out.println(String.format("%s%s%s%s_%s%s%s_%s",PIC_BASE_PATH , ALBUM_PIC_BASE_PATH ,File.separator,"%s","%s",File.separator,"%s","%s"));


//        DownloadPicFromURL downloadPicFromURL = new DownloadPicFromURL();
//
//        downloadPicFromURL.downloadPictureByUrlName("https://cdn-msp2.jm-comic1.club/media/photos/480485/00035.webp","D:\\data\\jm\\test");

    }

    static <T> Field getFiled(SFunction<T,?> fn) throws Exception{
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
