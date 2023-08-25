package cn.wang.javdbdownload;

import cn.hutool.core.collection.CollectionUtil;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.Theme;
import cn.wang.javdbdownload.jm.service.AlbumService;
import cn.wang.javdbdownload.jm.service.ThemeService;
import cn.wang.javdbdownload.jm.service.impl.ThemeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

@SpringBootTest
class JavdbDownloadApplicationTests {

    @Resource
    private ThemeService themeService;
    @Resource
    private AlbumService albumService;

    @Test
    void contextLoads() {

//        List<Theme> themeList = themeService.list();
//        CountDownLatch countDownLatch = batchRun(themeList, (e -> themeService.getAlbumByThemeId(e.getId())), 10);
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        List<Album> albumList = albumService.list();
        int threadCount = 24;
        int threadListSize = albumList.size() / threadCount;
        List<List<Album>> split = CollectionUtil.split(albumList, threadListSize);
        AtomicInteger atomicInteger = new AtomicInteger();
        for (List<Album> albums : split) {
            atomicInteger.incrementAndGet();
            new Thread(() -> {
                albums.forEach(e -> albumService.flushDataByWeb(e.getId()));
                atomicInteger.decrementAndGet();
            }).start();
        }

        while (atomicInteger.get() != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    static <T> CountDownLatch batchRun(List<T> list, Consumer<? super T> action, int threadCount) {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        Object lock = new Object();

        Iterator<T> iterator = list.iterator();

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                T t = null;
                synchronized (lock) {
                    if (iterator.hasNext()){
                        t = iterator.next();
                    }
                }
                while (t != null){
                    action.accept(t);
                    synchronized (lock) {
                        if (iterator.hasNext()){
                            t = iterator.next();
                        }
                    }
                }

                countDownLatch.countDown();

            }).start();
        }

        return countDownLatch;

    }

}
