package com.dh.usersService.feignInterface;

//import com.dh.userservice.configuration.feign.FeignInterceptor;
import com.dh.usersService.configuracion.security.feign.FeignInterceptor;

import com.dh.usersService.model.Bill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name= "ms-bill",url = "http://localhost:8087", configuration = FeignInterceptor.class)
public interface IFeignMsBills {


    @RequestMapping(method = RequestMethod.GET,value = "/bills/facturasId")
    ResponseEntity<List<Bill>> findByidBill(@RequestParam String customerBill);



}
