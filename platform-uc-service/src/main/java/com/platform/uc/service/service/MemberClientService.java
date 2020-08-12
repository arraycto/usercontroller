package com.platform.uc.service.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.uc.service.mapper.MemberClientMapper;
import com.platform.uc.service.vo.MemberClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MemberClientService {

    @Resource private MemberClientMapper memberClientMapper;

    /**
     * 保存用户平台
     * @param memberClient
     */
    public int saveUserRole(MemberClient memberClient) {
        if (memberClient ==null){
            throw new RuntimeException("菜单对象为空");
        }
        memberClient.setCreateDate(new Date());

        int insert = memberClientMapper.insert(memberClient);
        return insert;
    }

    /**
     * 通过用户ID获取平台信息
     * @param mid
     * @return
     */
    public List<MemberClient> selectByUserId(String mid){
        QueryWrapper<MemberClient> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(mid)) {
            wrapper.eq("mid", mid);
        }
        List<MemberClient> listRole=  memberClientMapper.selectList(wrapper);
        return listRole;
    }

    /**
     * 通过用户ID获取平台信息
     * @param id
     * @return
     */
    public MemberClient selectById(String id){
        QueryWrapper<MemberClient> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        return memberClientMapper.selectById(wrapper);
    }

    /**
     * 更新平台
     *
     * @param memberClient
     */
    public int updateUserRole(MemberClient memberClient) {

        QueryWrapper<MemberClient> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(memberClient.getClientId())) {
            wrapper.eq("client_id", memberClient.getClientId());
        }

        return memberClientMapper.update(memberClient, wrapper);
    }

    /**
     * 删除平台信息
     * @param id
     */
    public int deleteById(String id) {
        return memberClientMapper.deleteById(id);
    }

    /**
     * 批量删除平台信息
     * @param idList
     */
    public int batchDelete(List<String> idList) {
        for (String id: idList) {
            memberClientMapper.deleteById(id);
        }
        return 1;
    }

}
