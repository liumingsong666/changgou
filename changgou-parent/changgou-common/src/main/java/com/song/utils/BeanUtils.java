/**
 * flyiu.com Inc.
 * Copyright (c) 2018-2018 All Rights Reserved.
 */
package com.song.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/** 
 * <p>对象转换
 * </p>
 *
 */
@Slf4j
public class BeanUtils {

    private static ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
    
    public static <T> String toString(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
           e.printStackTrace();
           return null;
        }
    }
    
    public static <T> T toObject(String content,Class<T> cls) {
        try {
            return objectMapper.readValue(content, cls);
        } catch (IOException e) {
           e.printStackTrace();
           return null;
        }
    }

    static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString(dateFormatter.format(value));
        }
    }
    
    static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString(dateFormatter.format(value));
        }
    }

    /**
     * 对象类型转换
     * 
     * @param src
     * @param source
     * @return
     */
    public static <T,S> List<T> toNewType(List<S> src, Class<T> source){
        return src.stream().map(i->toNewType(i, source)).collect(Collectors.toList());
    }

    /**
     * 将原始对象转换为新类型
     * 
     * @param src
     * @param source
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static <T> T toNewType(Object src, Class<T> source) {

        
//        
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(LocalDate.class, new LocalDateSerializer());
////        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
////        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.registerModule(module);
        try {
            //FIXME 此处对于循环递归会有问题，请注意使用
            return objectMapper.readValue(objectMapper.writeValueAsString(src), source);
            
  //         T t = source.newInstance();
  //          org.springframework.beans.BeanUtils.copyProperties(src,t);
  //          return t;
//            return objectMapper.readValue(objectMapper.writeValueAsString(src), source);
        } catch (Exception e) {
            log.error("这里报错了", e); 
        }
        return null;
    }
    
    
    
    public static <T> T toNewTypeReference(Object src, TypeReference<T> source) {
//      
//      SimpleModule module = new SimpleModule();
//      module.addSerializer(LocalDate.class, new LocalDateSerializer());
////      module.addSerializer(LocalTime.class, new LocalTimeSerializer());
////      module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//      objectMapper.registerModule(module);
      try {
          return objectMapper.readValue(objectMapper.writeValueAsString(src), source);
          
//         T t = source.newInstance();
//          org.springframework.beans.BeanUtils.copyProperties(src,t);
//          return t;
//          return objectMapper.readValue(objectMapper.writeValueAsString(src), source);
      } catch (Exception e) {
          log.error("", e);
      }
      return null;
  }
}
