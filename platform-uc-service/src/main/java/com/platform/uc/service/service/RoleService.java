package com.platform.uc.service.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.uc.service.mapper.MemberRoleMapper;
import com.platform.uc.service.mapper.UcRoleMapper;
import com.platform.uc.service.mapper.UcRolePermissionMapper;
import com.platform.uc.service.vo.MemberRole;
import com.platform.uc.api.vo.request.MeunPermissionVo;
import com.platform.uc.service.vo.UcRole;
import com.platform.uc.service.vo.UcRolePermission;
import javafx.scene.control.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service public class RoleService
{
	@Autowired UcRoleMapper ucRoleMapper;

	@Autowired MemberRoleMapper memberRoleMapper;

	@Autowired UcRolePermissionMapper ucRolePermissionMapper;

	public void insert(UcRole ucRole)
	{
		ucRoleMapper.insert(ucRole);
	}

	public void update(UcRole ucRole)
	{
		ucRoleMapper.updateById(ucRole);
	}

	public UcRole selectBean(String id)
	{
		return ucRoleMapper.selectById(id);
	}

	public IPage<UcRole> selectList(String name, Integer pageNum, Integer pageSize)
	{
		Page<UcRole> dataElementPage = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
		QueryWrapper<UcRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("state", 0);
		if (StringUtils.isNotEmpty(name))
		{
			queryWrapper.like("name", name);
		}
		return ucRoleMapper.selectPage(dataElementPage, queryWrapper);
	}

	public IPage<MemberRole> selectRoleUsers(String roleId, Integer pageNum, Integer pageSize)
	{

		Page<MemberRole> page = new Page<>( pageNum == null ? 1 : pageNum,pageSize == null ? 10 : pageSize);// 当前页，总条数 构造 page 对象
		return page.setRecords(memberRoleMapper.selectList(page, roleId));
	}

	public void insertRolePermission(UcRolePermission ucRolePermission)
	{

		ucRolePermissionMapper.insert(ucRolePermission);
	}
}
