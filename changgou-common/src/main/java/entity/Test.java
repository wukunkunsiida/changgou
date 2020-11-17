package entity;

import com.sun.nio.zipfs.ZipInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther wkk
 * @date 2020/11/10 10:05
 */
public class Test {

    public static void main(String[] args) {


//        int[] nums={2,7,11,2};
//        int b=13;
//
//        Map<Integer,Integer> c=new HashMap<>();
//        for (int i=0;i<nums.length;i++){
//            if (c.containsKey(b-nums[i])){
//                System.out.println(b-nums[i]);
//                System.out.println(nums[i]);
//                return;
//            }
//            c.put(nums[i],i);
//        }
        Object object = new Object();
        System.out.println(object.getClass().getClassLoader());

        ZipInfo zipInfo = new ZipInfo();
        System.out.println(zipInfo.getClass().getClassLoader());

        System.out.println(Test.class.getClassLoader());
   // }

  //  public class Test {

       // public static void main(String[] args) {
            new Test().method1();
        }

        public void method1() {
            System.out.println("method1 start");
            method2();
            System.out.println("method1 finish");
        }

        private void method2() {
            System.out.println("method2 start");
            method3();
            System.out.println("method2 finish");
        }

        private void method3() {
            System.out.println("method3 start");
            method4();
            System.out.println("method3 finish");
        }

        private void method4() {
            System.out.println("method4 start");
            System.out.println("method4 finish");
        }


}
