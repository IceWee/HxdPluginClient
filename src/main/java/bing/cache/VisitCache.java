package bing.cache;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

/**
 * 历史请求数据缓存
 *
 * @author IceWee
 */
public class VisitCache implements Comparable<VisitCache>, Serializable {

    private String url;
    private String req;

    public VisitCache() {
        super();
    }
    
    public VisitCache(String url) {
        this.url = url;
    }

    public VisitCache(String url, String req) {
        this(url);
        this.req = req;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        VisitCache other = (VisitCache) obj;
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!StringUtils.equals(url, other.getUrl())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(VisitCache o) {
        if (this != null && o != null && url != null && o.getUrl() != null) {
            return url.compareTo(o.getUrl());
        }
        return 0;
    }

    @Override
    public String toString() {
        return url;
    }

}
