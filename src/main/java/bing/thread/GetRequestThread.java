package bing.thread;

import bing.AppUI;
import bing.util.ExceptionUtils;
import bing.util.HttpClientUtils;
import exception.OperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务请求线程
 *
 * @author IceWee
 */
public class GetRequestThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetRequestThread.class);
    private final AppUI app; // 主应用
    private String url; // 请求URL
    private String req; // 请求内容
    private boolean send; // 发送请求开关

    public GetRequestThread(AppUI app, String url, String req, boolean send) {
        super();
        this.app = app;
        this.url = url;
        this.req = req;
        this.send = send;
    }
    
    /**
     * 发送请求
     *
     * @param url
     * @param req
     */
    public void sendRequest(String url, String req) {
        this.url = url;
        this.req = req;
        setSend(true);
    }

    @Override
    public void run() {
        while (true) {
            listenRequest();
            wait(100); // 等待
        }
    }

    /**
     * 发送Get请求
     */
    private void listenRequest() {
        if (isSend()) {
            try {
                String res = HttpClientUtils.httpGet(url, req);
                LOGGER.info(this.getName() + " 线程 HTTP(200) 服务成功调用...");
                setSend(false);
                app.getRequestCallback(url, req, res);
            } catch (OperationException e) {
                setSend(false);
                LOGGER.error(e.getMessage());
            } catch (Exception e) {
                setSend(false);
                LOGGER.debug(ExceptionUtils.createExceptionString(e));
                LOGGER.error("请求失败，详情查看日志");
            } finally {
                app.sendButtonEnable(true);
            }
        }
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

    private boolean isSend() {
        return send;
    }
    
    private void setSend(boolean send) {
        this.send = send;
    }

}
