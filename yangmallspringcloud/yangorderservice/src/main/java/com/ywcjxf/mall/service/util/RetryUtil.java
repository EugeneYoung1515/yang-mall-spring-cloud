package com.ywcjxf.mall.service.util;

import java.util.Map;

public class RetryUtil {
    public static void retry (int attempt, int interval, Map<Object,Boolean> retryPolicy, Handler handler, Recoverer recoverer) throws Exception{
        //true表示重试 false表示不重试 但是还是会走Recoverer
        //没在map里的表示 异常继续抛出抛出 不会走Recoverer


        Exception exception = null;

        for(int i=0;i<attempt+1;i++){//要优化 attempt是0 也就是不重试 但是下面的afterRun也会执行

            //自己的重试类还要加内容
            //重试次数等于0 就是不重试
            //要把最后一次重试的异常传给Recoverer

            try {
                handler.run();
                return;
            }catch (Exception ex){

                if(attempt!=0) {
                    Boolean b = retryPolicy.get(ex.getClass());
                    if (b != null && !b) {
                        exception = ex;
                        break;
                    }
                    if (b != null && b) {
                        //压掉异常 继续下一个循环

                        if(i==attempt){
                            exception = ex;
                        }
                    }
                    if (b == null) {
                        throw ex;
                    }
                }else{
                    throw ex;
                }
            }

            if(i!=attempt) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    //这里要不要换成日志
                }
            }
        }

        recoverer.afterRun(exception);
    }

    public static interface Handler{
        void run() throws Exception;
    }

    public static interface Recoverer{
        void afterRun(Exception ex);
    }
}
