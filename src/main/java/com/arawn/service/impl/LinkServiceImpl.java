package com.arawn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arawn.dao.LinkDao;
import com.arawn.entity.Link;
import com.arawn.service.LinkService;

/**
 * 友情链接Service实现类
 * @author Administrator
 *
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService {

	@Resource
	private LinkDao linkDao;

	@Override
	public List<Link> list(Map<String, Object> map) {
		return linkDao.list(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return linkDao.getTotal(map);
	}

	@Override
	public Integer add(Link link) {
		return linkDao.add(link);
	}

	@Override
	public Integer update(Link link) {
		return linkDao.update(link);
	}

	@Override
	public Integer delete(Integer id) {
		return linkDao.delete(id);
	}
}
