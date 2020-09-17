package entity;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @auther wkk
 * @date 2020/8/11 15:14
 */



public class FeignInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes!=null){
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
          if (request!=null){
              Enumeration<String> headerNames = request.getHeaderNames();
             if (headerNames!=null){
                while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                    System.out.println(headerName+"1111");
                    if (headerName.equals("authorization")){
                String headerValue = request.getHeader(headerName);
                template.header(headerName,headerValue); }
            }
          }
         }
        }
     }
    }

