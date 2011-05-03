package com.elf.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.elf.dao.UserDao;
import com.elf.entities.User;

@Repository("userDao")
public class UserDaoImpl extends SqlMapClientDaoSupport implements UserDao {

    @Override
    public int selectLoginNameCount(String loginName) {
        return (Integer) getSqlMapClientTemplate().queryForObject("users.selectLoginNameCount",loginName);
    }

    @Override
    public void insert(User user) {
        getSqlMapClientTemplate().insert("users.insert", user);
    }

    @Override
    public User selectSingleUser(User user) {
        return (User) getSqlMapClientTemplate().queryForObject("users.selectSingleUser", user);
    }

	@Override
	public User modifyPassword(User user) {
		getSqlMapClientTemplate().update("users.modifyPassword", user);
		return selectSingleUser(user);
	}

}
