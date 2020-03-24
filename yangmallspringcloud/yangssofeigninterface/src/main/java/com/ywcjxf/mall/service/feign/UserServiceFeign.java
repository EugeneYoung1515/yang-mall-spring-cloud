package com.ywcjxf.mall.service.feign;

import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.UserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserServiceFeign extends UserService {

    @Override
    @GetMapping("/sendMessageCode")
    int sendMessageCode(@RequestParam("phoneNumber") String phoneNumber);

    @Override
    @PostMapping("/loginOrRegister")
    User loginOrRegister(@RequestParam("phoneNumber") String phoneNumber,@RequestBody User user);
}
