package com.social.media.confessionmedia.resourceserver.caches;

import com.social.media.confessionmedia.resourceserver.dto.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CachePostManager {
    
    private static Map<String, List<PostResponse>> llhPostCache = null;
    private static final int CAPACITY = 2;

    // Capacity is 2, loadFactor 75% , auto evict the oldest entry
    private static Map<?,?> getInstanceOfAlPostCaches () {
      if(llhPostCache == null){
          llhPostCache  = new LinkedHashMap<>(2, 0.75f, true){
              @Override
              protected boolean removeEldestEntry(Map.Entry<String, List<PostResponse>> eldest) {
                  return size() > CAPACITY;
              }
          };
      }
      return llhPostCache;
    }

    public static List<PostResponse> getPostFromCached(){
        String key = CacheKeyUtil.generatePostCacheKeyByMinute();
        getInstanceOfAlPostCaches();

        List<PostResponse> listPosts =  llhPostCache.get(key);
        log.info("Pull data from cached");
        return listPosts;
    }

    public static void putDataToCache(List<PostResponse> listPosts){
        String key = CacheKeyUtil.generatePostCacheKeyByMinute();
        getInstanceOfAlPostCaches();
        synchronized (llhPostCache) {
            llhPostCache.put(key, listPosts);
        }
        log.info("Put data set to POST cached, current size is " + llhPostCache.size());
    }

}
