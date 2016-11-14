package cn.autotest.framework.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by MingfengMa .
 * Data : 2016/7/14
 * Desc :
 */
public class ThreadUtils {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int processors = Runtime.getRuntime().availableProcessors();
    private int thread_num ;
    private int client_num;



    public ThreadUtils(){

    }

    public ThreadUtils(int thread_num,int client_num){
        this.thread_num = thread_num;
        this.client_num = client_num;
    }

    public void hello(Integer integer,String name){
        logger.info("hello - " + name);
    }

    /**
     * 创建线程池服务
     * @author  mingfengma
     * @return
     */
    public ExecutorService createThreadPool(){
        return Executors.newFixedThreadPool(processors);
    }


    /**
     * @Author MingfengMa
     * @Date 2016/7/15
     * @param
     * @Description
     *
     */
    public void aSyncClientRequest(){
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault();
        boolean isret = false;
        HttpResponse response;
        httpAsyncClient.start();
        try {
            HttpGet request = new HttpGet("http://localhost:5000/getstocks");
            Future<HttpResponse> future = httpAsyncClient.execute(request,null);
            response = future.get();
            if (response !=null){
                isret =true;
                logger.info("Invoke response : " + response.getEntity().getContent().toString());
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        } finally {
            while (isret){
                try {
                    httpAsyncClient.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * @author mingfengma
     * 执行线程
     * @param claz      传入所需要的类
     * @param method    传入所需要的方法
     */
    public void executeThread(final Class claz, String method, final Object... params){

        assert claz!=null && method != null;
        final CountDownLatch countDownLatch = new CountDownLatch(thread_num);
        final List<Runnable> runnables = new ArrayList<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(thread_num);
        logger.info("threads num :" + thread_num);
        final Method method1 = ReflectorHelper.getMethod(claz,method);
        logger.info("调用方法名:" + method1.getName() + " - invoke times : " + client_num);
        final int[] invokeTimes = ThreadUtils.this.getInvokeTimes(thread_num,client_num);

        for (int i = 0; i< thread_num; i++){
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < invokeTimes[0]; j++){
                            logger.info("invoke-start .....");
                            ReflectionUtils.invokeMethod(
                                    method1,
                                    Class.forName(claz.getName()).newInstance(),
                                    params);
                        }
                    } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();

                    }
                }
            };
            runnables.add(runnable);
        }
        invokeProcess(runnables,executorService);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    /**
     * @Author MingfengMa
     * @Date 2016/9/21
     * @param
     * @Description  根据执行次数平均分组
     *
     */
    private int[] getInvokeTimes(int threadnum, int clientnum){
        int miss = clientnum % threadnum;
        int rtime = clientnum/threadnum;
        int[] invokeTimes = new int[threadnum];

        for (int thread = 0; thread< threadnum; thread++ ){
            if (thread< threadnum-1){
                invokeTimes[thread] = rtime;
            }else if (thread == threadnum-1){
                invokeTimes[thread] = rtime + miss;
            }
        }
        logger.info("After split : " + invokeTimes[0]);
        return invokeTimes;
    }

    /**
     * @Author MingfengMa
     * @Date 2016/9/21
     * @param
     * @Description  循环调用任务
     *
     */
    private void invokeProcess(List<Runnable> runnables, ExecutorService executorService){
        for (Runnable runnable:runnables){
            executorService.execute(runnable);
        }
    }




    public static void main(String[] args) {
        ThreadUtils threadUtils = new ThreadUtils(3,10000);
        threadUtils.executeThread(ThreadUtils.class,"hello",new Integer("1"),"马铭锋");
        //threadUtils.aSyncClientRequest();
    }


}
