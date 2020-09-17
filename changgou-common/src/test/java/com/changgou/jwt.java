package com.changgou;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @auther wkk
 * @date 2020/7/5 10:28
 */
public class jwt {

    @Test
   public void testCreateTest(){
        JwtBuilder builder=Jwts.builder();
        builder.setIssuer("吴坤坤");
        builder.setSubject("测试");
        builder.setIssuedAt(new Date());
        builder.signWith(SignatureAlgorithm.HS256,"itcast1");
        System.out.println(builder.compact());
    }

   @Test
    public void parseToken(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLlkLTlnaTlnaQiLCJzdWIiOiLmtYvor5UiLCJpYXQiOjE1OTM5MTc4NTJ9.TZR-Gd6LmH9zaJVlPamR3sGjUjHQnq4zMKWhjkpPXhI";
        Claims claims = Jwts.parser().setSigningKey("itcast1").parseClaimsJws(token).getBody();
        System.out.println(claims.toString());
    }

    @Test
    public void parseToken1()throws Exception{
        String str=WXPayUtil.generateNonceStr();
        System.out.println("随机串"+str);
        Map map=new HashMap();
        map.put("id","222");
        map.put("tille","2222");
        map.put("mony","2222");
        Map neMap=new HashMap();
        neMap.put("age","随机串");
        map.put("neMap",neMap);
       // String aaa=WXPayUtil.generateSignedXml(map,"cast");
       //// String abbbaa=WXPayUtil.mapToXml(map);
       // System.out.println(abbbaa);
        String parentName = "xml";
        Document doc = DocumentHelper.createDocument();
        doc.addElement(parentName);
        String xml = recursionMapToXml(doc.getRootElement(), parentName, map, false);
        String a= formatXML(xml);
        System.out.println(a);



        Map body = new HashMap();
        body.put("1","1");
        body.put("2","2");

        String jsonData = JSON.toJSONString(body);
        MultiValueMap<String, String> mapq = new LinkedMultiValueMap<>();
        mapq.add("1", "2");
        mapq.add("2", "2");
        mapq.add("3", "3");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:9200/oauth/login").queryParams(mapq);
       //ResponseEntity<JSONObject> responseEntity = template.postForEntity(builder.toUriString(), request, JSONObject.class);
        System.out.println(builder.toUriString());
       // System.out.println(responseEntity);


    }


    public static String formatXML(String xml) {
        String requestXML = null;
        try {
            // 拿取解析器
            SAXReader reader = new SAXReader();
            Document document = reader.read(new StringReader(xml));
            if (null != document) {
                StringWriter stringWriter = new StringWriter();
                // 格式化,每一级前的空格
                OutputFormat format = new OutputFormat("	", true);
                // xml声明与内容是否添加空行
                format.setNewLineAfterDeclaration(false);
                // 是否设置xml声明头部
                format.setSuppressDeclaration(false);
                // 是否分行
                format.setNewlines(true);
                XMLWriter writer = new XMLWriter(stringWriter, format);
                writer.write(document);
                writer.flush();
                writer.close();
                requestXML = stringWriter.getBuffer().toString();
            }
            return requestXML;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String recursionMapToXml(Element element, String parentName, Map<String, Object> map, boolean isCDATA) {
        Element xmlElement = element.addElement(parentName);
        map.keySet().forEach(key -> {
            Object obj = map.get(key);
            if (obj instanceof Map) {
                recursionMapToXml(xmlElement, key, (Map<String, Object>)obj, isCDATA);
            } else {
                String value = obj == null ? "" : obj.toString();
                if (isCDATA) {
                    xmlElement.addElement(key).addCDATA(value);
                } else {
                    xmlElement.addElement(key).addText(value);
                }
            }
        });
        return xmlElement.asXML();
    }

}
