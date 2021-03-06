package com.platform.uc.api;

import com.platform.uc.api.vo.request.BatchRequest;
import com.platform.uc.api.vo.request.ChangeStatusRequest;
import com.platform.uc.api.vo.request.MemberClientRequest;
import com.platform.uc.api.vo.request.QueryClientUserRequest;
import com.platform.uc.api.vo.response.ClientMemberResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 远程用户应用关系信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/member/client")
public interface RemoteMemberClientService {


    @PostMapping("/query")
    BizPageResponse<ClientMemberResponse> selectUsersPage(@RequestBody QueryClientUserRequest request);

    /**
     * 保存平台用户  用平台与用户关联
     */
    @PostMapping("/")
    BizResponse<Void> save(@RequestBody MemberClientRequest request);

    /**
     * 批量删除平台用户 假删除
     */
    @PostMapping("/change/status")
    BizResponse<Void> changeStatus(@RequestBody ChangeStatusRequest request);

    /**
     * 批量删除平台用户 真删除
     */
    @PostMapping("/remove")
    @ApiOperation(value = "批量删除")
    BizResponse<Void> batchRemove(@RequestBody BatchRequest request);
}
