package com.ywcjxf.mall.service.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ywcjxf.mall.pojo.Spec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper mapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//这几个常量的意思是
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeySerializer(Spec.class,new CustomKeySerializer(mapper));
        simpleModule.addKeyDeserializer(Spec.class,new CustomKeyDeserializer(mapper));

        mapper.registerModule(simpleModule);

        return mapper;
    }

    private static class CustomKeySerializer extends JsonSerializer<Spec> {

        private ObjectMapper objectMapper;

        public CustomKeySerializer(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public void serialize(Spec spec, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeFieldName(objectMapper.writeValueAsString(spec));
        }
    }

    private static class CustomKeyDeserializer extends KeyDeserializer {

        private ObjectMapper objectMapper;

        public CustomKeyDeserializer(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Object deserializeKey(String key, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return objectMapper.readValue(key, Spec.class);
        }
    }

}
