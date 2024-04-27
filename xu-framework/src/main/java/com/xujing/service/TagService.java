package com.xujing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.TagListDto;
import com.xujing.domain.entity.Tag;
import com.xujing.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-12-10 13:10:37
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagVo tagVo);

    ResponseResult deleteTag(Long id);

    ResponseResult getTag(Long id);

    ResponseResult updateTag(TagVo tagVo);

    ResponseResult listAllTag();
}

