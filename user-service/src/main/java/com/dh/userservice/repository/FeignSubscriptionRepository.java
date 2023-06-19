package com.dh.userservice.repository;

import com.dh.userservice.configuration.feign.FeignInterceptor;
import com.dh.userservice.configuration.feign.OAuthFeignConfig;
import com.dh.userservice.model.SubscriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "subscription-service",url = "http://localhost:8083", configuration = FeignInterceptor.class)
public interface FeignSubscriptionRepository {

    @RequestMapping(method = RequestMethod.GET,value = "/subscription/find")
    ResponseEntity<SubscriptionDTO> findByUserId(@RequestParam Integer userId);
}
