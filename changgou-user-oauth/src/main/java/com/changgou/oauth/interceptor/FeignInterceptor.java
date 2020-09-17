package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.omg.PortableInterceptor.RequestInfoOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
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
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes!=null){
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        if (request!=null){
//            Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames!=null){
//            while (headerNames.hasMoreElements()){
//                String headerName = headerNames.nextElement();
//                if (headerName.equals("authorization")){
//                String headerValue = request.getHeader(headerName);
//                template.header(headerName,headerValue); }
//            }
//        }
//        }
//        }

        String token=AdminToken.admintoken();
        template.header("Authorization","bearer "+token);
    }
    }

