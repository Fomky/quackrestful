package org.fomky.ratpack.core.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * 
 * @author xuliang
 *
 */
public class JSONUtil {
	
	private static ObjectMapper mapper=new ObjectMapper();
	/**
	 * @param object
	 * @return
	 */
   public static String toJSON(Object object){
       try {
           return mapper.writeValueAsString(object);
       } catch (JsonProcessingException e) {
           return "";
       }
   }
   /**
    * 转换为对象
    * @param jsonString
    * @param clazz
    * @return
    * @throws Exception
    */
   public static <T> T toObject(String jsonString, Class<T> clazz)throws Exception {
	  return mapper.readValue(jsonString, clazz);
   }
   /**
    * 转换为json对象
    * @param jsonString
    * @return
    * @throws Exception
    */
   public static JsonNode read(String jsonString)throws Exception {
	   return mapper.readTree(jsonString);
   }
   /**
    * 转换为列表对象
    * @param jsonString
    * @param clazz
    * @return
    * @throws Exception
    */
   public static <T> List<T> toList(String jsonString, Class<T> clazz) throws Exception {
	   JavaType javaType=mapper.getTypeFactory().constructCollectionType(List.class, clazz);
	   return mapper.readValue(jsonString,javaType);
   }
}
