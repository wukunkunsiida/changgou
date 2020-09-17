package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "goods")
public interface SkuFeign {

     @GetMapping("sku/spu/1148477873158365184")
     //@GetMapping("/sku")
     List<Sku> findAll();

     @GetMapping("/sku/spu/{spuId}")
     public List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId);

     @GetMapping("/sku/{id}")
     public Result<Sku> findById(@PathVariable("id") String id);


     @GetMapping(value = "/sku/desc/count")
     public Result deCrCount(@RequestParam Map<String,String> descmap);
}
