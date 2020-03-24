package com.ywcjxf.mall.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ywcjxf.mall.dao.UserMapper;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private AliMessageProperties aliMessageProperties;

    @Override
    public int sendMessageCode(String phoneNumber) {
        int random = ThreadLocalRandom.current().nextInt(1000,10000);

        DefaultProfile profile = DefaultProfile.getProfile(aliMessageProperties.getRegionId(), aliMessageProperties.getAccessKeyId(), aliMessageProperties.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(aliMessageProperties.getDomain());
        request.setVersion(aliMessageProperties.getVersion());
        request.setAction(aliMessageProperties.getAction());

        request.putQueryParameter("PhoneNumbers", phoneNumber);

        request.putQueryParameter("TemplateCode", aliMessageProperties.getTemplateCode());
        request.putQueryParameter("SignName", aliMessageProperties.getSignName());

        request.putQueryParameter("TemplateParam", "{\"code\":"+random+"}");

        System.out.println("-----\n"+aliMessageProperties);
        try {
            CommonResponse response = client.getCommonResponse(request);
            //System.out.println(response.getHttpResponse().getHttpContentString());
            logger.info(response.getHttpResponse().getHttpContentString());

        } catch (ServerException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(),e);
        } catch (ClientException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(),e);
        }

        return random;
    }

    @Override
    public User loginOrRegister(String phoneNumber,User user) {
        //int num = userMapper.selectCountByPhoneNumber(phoneNumber);
        //if(num>0){

        //}

        User user2 = userMapper.selectByPhoneNumber(phoneNumber);
        if(user2!=null){
            user2.setLastVisit(user.getLastVisit());
            user2.setLastIp(user.getLastIp());

            userMapper.updateByPrimaryKeySelective(user2);

            return user2;
        }else{
            user.setPhoneNumber(phoneNumber);
            user.setCreateDate(user.getLastVisit());
            user.setUserName("u_"+getRandomString(10));

            userMapper.insertSelectiveAndReturnId(user);
            return user;
        }
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=ThreadLocalRandom.current().nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }



    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setAliMessageProperties(AliMessageProperties aliMessageProperties) {
        this.aliMessageProperties = aliMessageProperties;
    }
}
