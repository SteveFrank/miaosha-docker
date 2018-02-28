package com.imooc.miaosha.redis.key;

import com.imooc.miaosha.redis.prefix.BasePrefix;

/**
 * 避免强刷
 */
public class AccessKey extends BasePrefix {

	private AccessKey( int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access");
	}
	
}
