package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.LinkDto;
import com.xujing.domain.dto.LinkStatusDto;
import com.xujing.domain.entity.Link;
import com.xujing.domain.vo.LinkVo;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-12-04 20:01:30
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkDto linkDto);

    ResponseResult addLink(LinkVo linkVo);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(LinkVo linkVo);

    ResponseResult deleteLink(Long id);

    ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto);
}

