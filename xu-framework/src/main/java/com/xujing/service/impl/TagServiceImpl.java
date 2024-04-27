package com.xujing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xujing.constants.SystemConstants;
import com.xujing.domain.ResponseResult;
import com.xujing.domain.dto.TagListDto;
import com.xujing.domain.entity.Tag;
import com.xujing.domain.vo.PageVo;
import com.xujing.domain.vo.TagVo;
import com.xujing.enums.AppHttpCodeEnum;
import com.xujing.exception.SystemException;
import com.xujing.mapper.TagMapper;
import com.xujing.service.TagService;
import com.xujing.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-10 13:10:38
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        Page<Tag> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        page(page, queryWrapper);

        if(page.getRecords().size()==0 && pageNum>1){
            page.setCurrent(pageNum-1);
            page(page, queryWrapper);
        }

        List<Tag> tagList = page.getRecords();
        long total = page.getTotal();

        // 封装数据
        PageVo pageVo = new PageVo(tagList, total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagVo tagVo) {
        // 新增标签
        Tag tag = BeanCopyUtils.copyBean(tagVo, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        // 删除标签，逻辑删除
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = getById(id);
        if(Objects.isNull(tag)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(TagVo tagVo) {
        Tag tag = BeanCopyUtils.copyBean(tagVo, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> list = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }


}

