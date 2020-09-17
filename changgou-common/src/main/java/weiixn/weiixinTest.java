package weiixn;

import com.github.wxpay.sdk.WXPayUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther wkk
 * @date 2020/8/26 10:11
 */
public class weiixinTest {



     public void testDemo(){
         String str=WXPayUtil.generateNonceStr();
         System.out.println("随机串"+str);
         Map map=new HashMap();


     }

}
