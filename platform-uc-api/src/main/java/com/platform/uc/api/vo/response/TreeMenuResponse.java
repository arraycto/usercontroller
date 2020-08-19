package com.platform.uc.api.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * @author hao.yan
 */
@Data
public class TreeMenuResponse implements Serializable {

    private static final long serialVersionUID = -7451840060110765736L;

    private String id;

    private String parentId;

    /**
     * 菜单/按钮名称
     */
    private String name;

    /**
     * 对应路由path
     */
    private String path;

    private Integer type;

    private String remark;

    /**
     * 对应路由组件component
     */
    private String component;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private String permission;

    private String icon;

    private Integer sort;

    private List<TreeMenuResponse> children;

}