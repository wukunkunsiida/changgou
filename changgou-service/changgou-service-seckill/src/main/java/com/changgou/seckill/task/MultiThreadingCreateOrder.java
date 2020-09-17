package com.changgou.seckill.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @auther wkk
 * @date 2020/9/2 16:03
 */
@Component
public class MultiThreadingCreateOrder {

    @Async
    public void createOrder(){

          try{
              System.out.println("多线程执行");
              Thread.sleep(1000);
              System.out.println("多线程结束");
          }catch (Exception e){
              e.printStackTrace();
          }

    }


}
