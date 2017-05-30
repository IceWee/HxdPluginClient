package bing.thread;

import bing.cache.*;
import bing.AppUI;
import bing.util.CacheUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存线程
 *
 * @author IceWee
 */
public class CacheThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheThread.class);
    private final AppUI app;
    private static final int MAX_CACHE_SIZE = 100; // 最大缓存限制
    private static List<VisitCache> caches = new ArrayList<>();
    private static int cacheSize;

    public CacheThread(AppUI app) {
        super();
        this.app = app;
    }

    @Override
    public void run() {
        initCaches(); // 初始化缓存
        while (true) {
            refreshCache(); // 刷新缓存
            wait(100); // 等待
        }
    }

    /**
     * 添加缓存
     *
     * @param cache
     */
    public void addCache(VisitCache cache) {
        LOGGER.debug("addCache begin...");
        if (!caches.contains(cache) && cacheSize < MAX_CACHE_SIZE) {
            DefaultListModel<VisitCache> visitCacheModel = (DefaultListModel<VisitCache>) app.getVisitList().getModel();
            visitCacheModel.addElement(cache);
            caches.add(cache);
        }
        LOGGER.debug("addCache end...");
    }
    
    /**
     * 删除缓存
     *
     * @param cache
     */
    public void removeCache(VisitCache cache) {
        LOGGER.debug("removeCache begin...");
        if (caches.contains(cache)) {
            DefaultListModel<VisitCache> visitCacheModel = (DefaultListModel<VisitCache>) app.getVisitList().getModel();
            visitCacheModel.removeElement(cache);
            caches.remove(cache);
        }
        LOGGER.debug("removeCache end...");
    }
    
    /**
     * 清空缓存
     *
     */
    public void clearCache() {
        LOGGER.debug("clearCache begin...");
        if (!caches.isEmpty()) {
            DefaultListModel<VisitCache> visitCacheModel = (DefaultListModel<VisitCache>) app.getVisitList().getModel();
            visitCacheModel.clear();
            caches.clear();
        }
        LOGGER.debug("clearCache end...");
    }

    /**
     * 是否需要刷新缓存
     *
     * @return
     */
    private boolean shouldRefresh() {
        return cacheSize != caches.size();
    }

    /**
     * 初始化缓存
     */
    private void initCaches() {
        LOGGER.debug("initCaches begin...");
        try {
            caches = CacheUtils.readVisitCache();
        } catch (Exception e) {
            CacheUtils.writeVisitCache(caches); // 生成空缓存文件
        }
        DefaultListModel<VisitCache> visitCacheModel = new DefaultListModel<>();
        caches.forEach((cache) -> {
            visitCacheModel.addElement(cache);
        });
        app.getVisitList().setModel(visitCacheModel);
        sortCaches();
        resetCacheSize();
        LOGGER.debug("initCaches end...");
    }

    /**
     * 排序缓存
     */
    private void sortCaches() {
        LOGGER.debug("sortCaches begin...");
        Collections.sort(caches); // 排序
        LOGGER.debug("sortCaches end...");
    }

    /**
     * 等待
     *
     * @param mills
     */
    private void wait(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 刷新缓存
     */
    private void refreshCache() {
        if (shouldRefresh()) {
            LOGGER.debug("refreshCache begin...");
            sortCaches();
            try {
                CacheUtils.writeVisitCache(caches); // 写缓存文件
            } catch (Exception e) {
            } 
            resetCacheSize(); // 处理完毕后更新缓存大小
            LOGGER.debug("refreshCache end...");
        } 
    }

    /**
     * 重置缓存大小
     */
    private void resetCacheSize() {
        LOGGER.debug("resetCacheSize begin...");
        cacheSize = caches.size();
        LOGGER.debug("resetCacheSize end...");
    }

}
