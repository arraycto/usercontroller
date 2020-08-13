package com.platform.uc.api;

import com.platform.uc.api.vo.request.Org;
import com.platform.uc.api.vo.request.UcRole;
import com.platform.uc.api.vo.request.UcRolePermission;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程用户信息接口
 *
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/role") public interface RemoteRoleService
{

	@PostMapping("/addRole") public BizResponse<String> addRole(@RequestBody UcRole ucRole);

	@PutMapping("/updateRole") public BizResponse<String> updateRole(@RequestBody UcRole ucRole);

	@GetMapping("/selectRole/{id}") public BizResponse<UcRole> selectRole(@PathVariable Long id);

	@GetMapping("/selectRoleList/{name}/{pageNum}/{pageSize}") public BizResponse<Object> selectRoleList(@PathVariable String name, @PathVariable Integer pageNum,
			@PathVariable Integer pageSize);

	@DeleteMapping("/deleteRole/{id}") public BizResponse<String> deleteRole(@PathVariable Long id);

	@GetMapping("/selectRoleUsers/{roleId}/{pageNum}/{pageSize}") public BizResponse<Object> selectRoleUsers(@PathVariable Long roleId, @PathVariable Integer pageNum,
			@PathVariable Integer pageSize);

	@PostMapping("/addRolePermission") public void addRolePermission(@RequestBody UcRolePermission ucRolePermission);
}