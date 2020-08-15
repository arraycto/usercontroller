package com.platform.uc.api;


import com.platform.uc.api.vo.request.ChangeStatusRequest;
import com.platform.uc.api.vo.request.MenuRequest;
import com.platform.uc.api.vo.response.MenuResponse;
import com.platform.uc.api.vo.response.TreeMenuResponse;
import com.ztkj.framework.response.core.BizPageResponse;
import com.ztkj.framework.response.core.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 远程菜单的信息接口
 */
@FeignClient(value = "platform-uc-service", path = "/menu")
public interface RemoteMenuService {

    /**
     * 保存菜单
     */
    @PostMapping("/save")
    BizResponse<Void> save(@RequestBody MenuRequest request);

    /**
     * 更新菜单
     */
    @PutMapping("/{id}")
    BizResponse<Void> modify(@PathVariable String id, @RequestBody MenuRequest request);

    /**
     * 删除子菜单
     */
    @PostMapping("/remove")
    BizResponse<Void> remove(@RequestBody ChangeStatusRequest request);

    /**
     * 查询所有菜单目录,有序展示
     * @return
     */
    @GetMapping("/{pid}")
    BizPageResponse<MenuResponse> selectMenuByParentId(@PathVariable("pid") String parentId);

    /**
     * 树形菜单
     */
    @PostMapping("/tree")
    BizPageResponse<TreeMenuResponse> tree();

}
