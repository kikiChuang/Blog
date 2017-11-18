package com.arawn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arawn.dao.CommentDao;
import com.arawn.entity.Comment;
import com.arawn.service.CommentService;

/**
 * 评论Service实现类
 * @author Administrator
 *
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentDao commentDao;
	
	@Override
	public List<Comment> list(Map<String, Object> map) {
		return commentDao.list(map);
	}

	@Override
	public int add(Comment comment) {
		return commentDao.add(comment);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return commentDao.getTotal(map);
	}

	@Override
	public Integer update(Comment comment) {
		return commentDao.update(comment);
	}

	@Override
	public Integer delete(Integer id) {
		return commentDao.delete(id);
	}

}
