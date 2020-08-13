package com.platform.uc.api;

import com.platform.uc.api.vo.request.MemberRoleRequest;
import com.platform.uc.api.vo.request.QueryRoleUserRequest;
import com.platform.uc.api.vo.response.MemberRoleResponse;
import com.platform.uc.api.vo.response.RoleMemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程用户角色信息接口
 * @author hao.yan
 */

@FeignClient(value = "platform-uc-service", path = "/member/role")
public interface RemoteMemberRoleService {

    /**
     * 查询角色下的用户
     */
    @PostMapping("/query")
    BizPageResponse<MemberRoleResponse> selectByConditions(@RequestBody QueryRoleUserRequest request);

    @PostMapping
    @ApiOperation(value = "保存实体信息")
    BizResponse<Integer> saveMemberRole(@RequestBody MemberRoleRequest memberRole);

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    BizResponse<MemberRoleResponse> findById(@PathVariable String id) ;

    @PutMapping
    @ApiOperation(value = "修改实体信息")
    BizResponse<Integer> modify(@RequestBody MemberRoleRequest memberRole) ;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除实体信息")
    BizResponse<Integer> removeById(@PathVariable String id) ;

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "批量删除实体信息")
    BizResponse<Integer> batchRemove(@RequestBody List<String> idList) ;
}
