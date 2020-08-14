package com.platform.uc.api.vo.request;

import lombok.Data;

import java.io.Serializable;


/**
 * @author CDN
 * @desc 系统菜单表
 * Date: 2020-08-10
 */
@Data
public class MenuRequest implements Serializable{

    private static final long serialVersionUID = -2485397326130062396L;

    /**
     * 父菜单
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由
     */
    private String url;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(0--正常 9--删除)
     */
    private Integer status;

    /**
     * 操作人
     */
    private String operator;

}
