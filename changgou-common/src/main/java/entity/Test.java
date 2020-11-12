package entity;

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
        int[] nums={1,1,2,3};
        int low = 1, high = nums.length;
        while (low < high) {
           int mid =(int)(low + (high - low) * 0.5);
            int cnt = 0;
            for(int i = 0; i < nums.length; i++){
                if(nums[i] <= mid){
                    cnt++;
                }
                if (cnt <= mid) low = mid + 1;
                else high = mid;
            }
            System.out.println(low);
        }

    }

}
