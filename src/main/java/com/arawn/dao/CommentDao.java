package com.arawn.dao;

import java.util.List;
import java.util.Map;

import com.arawn.entity.Comment;

/**
 * ����Dao�ӿ�
 * @author Administrator
 *
 */
public interface CommentDao {
	
	/**
	 * ��ѯ�û�������Ϣ
	 * @param map
	 * @return
	 */
	public List<Comment> list(Map<String,Object> map);
	
	/**
	 * �������
	 * @param comment
	 * @return
	 */
	public int add(Comment comment);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * �޸�����
	 * @param comment
	 * @return
	 */
	public Integer update(Comment comment);
	
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);

}
