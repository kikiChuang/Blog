package com.arawn.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arawn.dao.BloggerDao;
import com.arawn.entity.Blogger;
import com.arawn.service.BloggerService;

/**
 * 博主Service实现类
 * @author Administrator
 *
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

	@Resource
	private BloggerDao bloggerDao;
	
	@Override
	public Blogger getByUserName(String userName) {
		return bloggerDao.getByUserName(userName);
	}

	@Override
	public Blogger find() {
		return bloggerDao.find();
	}

	@Override
	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}

}
