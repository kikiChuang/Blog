package com.arawn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arawn.dao.BlogDao;
import com.arawn.entity.Blog;
import com.arawn.service.BlogService;

/**
 * ����Serviceʵ����
 * @author Administrator
 *
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogDao blogDao;
	
	@Override
	public List<Blog> countList() {
		return blogDao.countList();
	}

	@Override
	public List<Blog> list(Map<String, Object> map) {
		return blogDao.list(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return blogDao.getTotal(map);
	}

	@Override
	public Blog findById(Integer id) {
		return blogDao.findById(id);
	}

	@Override
	public Integer update(Blog blog) {
		return blogDao.update(blog);
	}

	@Override
	public Blog getLastBlog(Integer id) {
		return blogDao.getLastBlog(id);
	}

	@Override
	public Blog getNextBlog(Integer id) {
		return blogDao.getNextBlog(id);
	}

	@Override
	public Integer add(Blog blog) {
		return blogDao.add(blog);
	}

	@Override
	public Integer delete(Integer id) {
		return blogDao.delete(id);
	}

	@Override
	public Integer getBlogByTypeId(Integer typeId) {
		return blogDao.getBlogByTypeId(typeId);
	}

	
}
