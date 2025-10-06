package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	public <T> T get(String key, Class<T> entityClass){
		try{
			Object o = redisTemplate.opsForValue().get(key);
			ObjectMapper mp=new ObjectMapper();
			if(o==null) return null;
			return mp.readValue(o.toString(),entityClass);
		}
		catch (Exception e){
			log.error("Error fetching data ",e);
			return null;
		}
	}
	public void set(String key,Object o,Long ttl){
		try{
			ObjectMapper mp=new ObjectMapper();
			String jsonValue = mp.writeValueAsString(o);
			redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.SECONDS);
		}
		catch (Exception e){
			log.error("Error fetching data ",e);
		}
	}
}
