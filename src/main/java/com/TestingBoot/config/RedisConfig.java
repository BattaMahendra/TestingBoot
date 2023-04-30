package com.TestingBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.TestingBoot.jms.RedisListener;

@Configuration
public class RedisConfig {
	
	 @Bean
	    public JedisConnectionFactory connectionFactory() {
	        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
	        configuration.setHostName("localhost");
	        configuration.setPort(6379);
	        return new JedisConnectionFactory(configuration);
	    }

	    @Bean
	    public RedisTemplate<String, Object> template() {
	        RedisTemplate<String, Object> template = new RedisTemplate<>();
	        template.setConnectionFactory(connectionFactory());
	        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
	        return template;
	    }
	    
	    @Bean
	    public ChannelTopic channel() {
	    	return new ChannelTopic("Publisher-Mahendra");
	    }
	    
	    @Bean
	    public MessageListenerAdapter messageListenerAdapter() {
	    	return new MessageListenerAdapter(new RedisListener());
	    }
	    
	    @Bean
	    public RedisMessageListenerContainer redisMessageListenerContainer() {
	    	RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
	    	listenerContainer.setConnectionFactory(connectionFactory());
	    	listenerContainer.addMessageListener(messageListenerAdapter(), channel());
	    	return listenerContainer;
	    }

}
