package com.TestingBoot.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import com.TestingBoot.entity.AnEntity;

@Component
public class RedisPublisher {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ChannelTopic channelTopic;
	
	
	public AnEntity publishMessage(AnEntity anEntity) {
		redisTemplate.convertAndSend(channelTopic.getTopic(), anEntity.toString());
		return anEntity;
	}

}
