package com.ywcjxf.mall.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

@Configuration
public class FeignConfig {

    /*
    static {
        System.out.println("----------");
    }

    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        System.out.println("feignDecoder");

        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        //return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
        return new OptionalDecoder(new ResponseEntityDecoder(new MySpringDecoder(objectFactory)));
    }

    @Bean
    public Encoder feignEncoder(ObjectMapper objectMapper) {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new SpringEncoder(objectFactory);
    }

    private static class MySpringDecoder extends SpringDecoder{
        public MySpringDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
            super(messageConverters);
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            System.out.println("-------\ndecode run");
            return super.decode(response, type);
        }
    }
    */
}
