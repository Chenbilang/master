package com.cbl.aa.biz;

import java.util.List;

import com.cbl.aa.entity.Role;
import com.cbl.aa.entity.Tree;

/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface RoleBiz extends BaseBiz<Role>{

	public List<Tree> readRoleMenus(Long uuid);

	public void updateRoleMenus(Long id, String checkedStr);
}

