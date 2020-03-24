package com.ywcjxf.mall.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PairDtoMapper{
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(PairDtoMapper.class);

    public Map<String,String> toMap(PairDto pairDto){

        /*
        Map<String,Object> map = new HashMap<>(16);
        Object first = pairDto.getFirst();
        serialize(map,first,"@@A@@");
        */

        Map<String,String> map = new HashMap<>(16);
        try {
            map.put("A",objectMapper.writeValueAsString(pairDto.getFirst()));
            map.put("B",objectMapper.writeValueAsString(pairDto.getSecond()));
            map.put("C",objectMapper.writeValueAsString(pairDto.getThird()));
            return map;
        }catch (JsonProcessingException ex){
            logger.error("objectMapper"+"_"+ex.getMessage(),ex);
        }
        return null;
    }

    public <A,B,C> PairDto<A,B,C> fromMap(Map<String,String> map,Class<A> f,Class<B> s,Class<C> t){
        PairDto<A,B,C> pairDto = new PairDto<>();
        try {
            pairDto.setFirst(objectMapper.readValue(map.get("A"),f));
            pairDto.setSecond(objectMapper.readValue(map.get("B"),s));
            pairDto.setThird(objectMapper.readValue(map.get("C"),t));

            return pairDto;
        }catch (IOException ex){
            logger.error(ex.getMessage(),ex);
        }
        return null;
    }

    public <A,B,C> PairDto<A,B,C> fromMap(Map<String,String> map, TypeReference f, TypeReference s, Class<C> t){
        PairDto<A,B,C> pairDto = new PairDto<>();
        try {
            pairDto.setFirst(objectMapper.readValue(map.get("A"),f));
            pairDto.setSecond(objectMapper.readValue(map.get("B"),s));
            pairDto.setThird(objectMapper.readValue(map.get("C"),t));

            return pairDto;
        }catch (IOException ex){
            logger.error(ex.getMessage(),ex);
        }
        return null;
    }

    public <A,B,C> PairDto<A,B,C> fromMap(Map<String,String> map, Class<A> f, TypeReference s, Class<C> t){
        PairDto<A,B,C> pairDto = new PairDto<>();
        try {
            pairDto.setFirst(objectMapper.readValue(map.get("A"),f));
            pairDto.setSecond(objectMapper.readValue(map.get("B"),s));
            pairDto.setThird(objectMapper.readValue(map.get("C"),t));

            return pairDto;
        }catch (IOException ex){
            logger.error(ex.getMessage(),ex);
        }
        return null;
    }


    /*
    private void serialize(Map<String,Object> map,Object o,String key){
        if(o instanceof Map){
            serializeMap(map,o,key);
        }else if(o instanceof PairDto){

        }else{
            serializeSimpleAndPojo(map,o,key);
        }
    }

    private void serializeMap(Map<String,Object> map,Object o,String key){
        Map source = (Map)o;
        source.forEach((k,v)->{

        });
    }

    private void serializeSimpleAndPojo(Map<String,Object> map,Object o,String key) throws JsonProcessingException{
        map.put(key,objectMapper.writeValueAsString(o));
    }
    */

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
