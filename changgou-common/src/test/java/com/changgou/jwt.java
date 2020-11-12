package com.changgou;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sun.misc.BASE64Encoder;

import javax.net.ssl.SSLContext;

/**
 * @auther wkk
 * @date 2020/7/5 10:28
 */
public class jwt {

    @Test
   public void testCreateTest()throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String logistics_interface = "<AddressMatch><ItemNo>9011234567810</ItemNo><ProductID>9011234567810</ProductID><IsPickupOrDelive>2</IsPickupOrDelive><MailType>B</MailType><DistCode>230102000000</DistCode><Address>黑龙江省哈尔滨市道里区安化街180号</Address><ProvName>黑龙江省</ProvName><CityName>哈尔滨市</CityName><ContyName>道里区</ContyName></AddressMatch>";

        String url = "https://211.156.195.17/iuni-job/a/iunicom/addrcheck";
        RestTemplate restTemplate=restTemplate1();

        //消息签名
        String sign = makeSignEMS(logistics_interface, "123456");
        System.out.println("data_digest=" + sign);
        String msg_type = "AddressCheck";
        String ecCompanyId = "CAINIAO";
        String data_digest = sign;
        HttpEntity<MultiValueMap> requestEntity = null;
        //发送报文
        try {
            String url1=url+"?"+"logistics_interface="+logistics_interface+"&data_digest="+data_digest+"&msg_type="+msg_type+"&ecCompanyId="+ecCompanyId;
            HttpHeaders headers = new HttpHeaders();
            //  MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity<MultiValueMap>(headers);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            HttpHeaders headerss = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("logistics_interface", logistics_interface);
            map.add("data_digest", data_digest);
            map.add("msg_type", msg_type);
            map.add("ecCompanyId", ecCompanyId);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headerss);
            ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

            ResponseEntity<String> exchange2 = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            System.out.println(exchange2.getBody());
            Map  retMap = WXPayUtil.xmlToMap(exchange2.getBody());
            System.out.println(retMap);
        } catch (Exception e) {
            System.out.println("返回");
           e.printStackTrace();
        }
    }


    public RestTemplate restTemplate1()
    {
        ClientHttpRequestFactory requestFactory = null;
        try {
            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        } catch (Exception e) {

        }
        return new RestTemplate(requestFactory);
    }
    private HttpClient httpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(65000) // 服务器返回数据(response)的时间，超时抛出read timeout
                .setConnectTimeout(65000) // 连接上服务器(握手成功)的时间，超时抛出connect timeout
                .setConnectionRequestTimeout(1000)// 从连接池中获取连接的超时时间，超时抛出ConnectionPoolTimeoutException
                .build();
        SSLContext sslContext = SSLContextBuilder.create().setProtocol(SSLConnectionSocketFactory.SSL).loadTrustMaterial((x, y) -> true).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setSSLContext(sslContext).setSSLHostnameVerifier((x, y) -> true).build();
    }

      /**
     * 进行md5和base64转换
     * @param str
     * @param partnered
     * @param charsetname
     * @return
     */
    public static String makeSignEMS(String data, String parentId)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String Ret=base64en.encode(MessageDigest.getInstance("MD5").digest((data+parentId).getBytes("UTF-8"))	);
        return Ret;
    }

    @Test
    public void testCreateTest2(){
        JwtBuilder builder=Jwts.builder();
        builder.setIssuer("吴坤坤");
        builder.setSubject("测试");
        builder.setIssuedAt(new Date());
        builder.signWith(SignatureAlgorithm.HS256,"itcast1");
        System.out.println(builder.compact());
    }

   @Test
    public void parseToken(){
//        String token="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLlkLTlnaTlnaQiLCJzdWIiOiLmtYvor5UiLCJpYXQiOjE1OTM5MTc4NTJ9.TZR-Gd6LmH9zaJVlPamR3sGjUjHQnq4zMKWhjkpPXhI";
//        Claims claims = Jwts.parser().setSigningKey("itcast1").parseClaimsJws(token).getBody();
//        System.out.println(claims.toString());
       //isBlank判断某字符串是否为空或长度为0或由空白符(whitespace)构成
       System.out.println("===============isBlank===============");
       System.out.println(StringUtils.isBlank(""));
       System.out.println(StringUtils.isBlank(null));
       System.out.println(StringUtils.isBlank(" "));
       System.out.println(StringUtils.isBlank("abc"));
       //对于制表符、换行符、换页符和回车符StringUtils.isBlank()均识为空白符
       System.out.println(StringUtils.isBlank("\t"));
       System.out.println(StringUtils.isBlank("\r"));
       System.out.println(StringUtils.isBlank("\n"));
       System.out.println(StringUtils.isBlank("\f"));
       //\b为单词边界符
       System.out.println(StringUtils.isBlank("\b"));
       //判断某字符串是否为空，为空的标准是str==null或str.length()==0
       System.out.println("===============isEmpty===============");
       System.out.println(StringUtils.isEmpty(""));
       System.out.println(StringUtils.isEmpty(null));
       System.out.println(StringUtils.isEmpty(" "));
       System.out.println(StringUtils.isEmpty("abc"));
       //对于制表符、换行符、换页符和回车符StringUtils.isEmpty()均识为非空字符串
       System.out.println(StringUtils.isEmpty("\t"));
       System.out.println(StringUtils.isEmpty("\r"));
       System.out.println(StringUtils.isEmpty("\n"));
       System.out.println(StringUtils.isEmpty("\f"));
       //\b为单词边界符
       System.out.println(StringUtils.isEmpty("\b"));
    }

    @Test
    public void parseToken1()throws Exception{
        String str=WXPayUtil.generateNonceStr();
        System.out.println("随机串"+str);
        Map map=new HashMap();
        map.put("id","222");
        map.put("tille","2222");
        map.put("mony","2222");
//        Map neMap=new HashMap();
//        neMap.put("age","随机串");
//        map.put("neMap",neMap);
        String aaa=WXPayUtil.generateSignedXml(map,"cast");
        String abbbaa=WXPayUtil.mapToXml(map);
        System.out.println(abbbaa);
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
