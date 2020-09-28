package com.accp.business.controller.admin;

import com.accp.server.domain.Chapter;
import com.accp.server.dto.ChapterDto;
import com.accp.server.dto.PageDto;
import com.accp.server.service.ChapterService;
import com.accp.server.util.UuidUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

@RequestMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto){
    chapterService.list(pageDto);
    return pageDto;
    }

    @RequestMapping("/save")
    public ChapterDto save(@RequestBody ChapterDto chapterDto){
    //表单新增
        //给chapter表的id 存储Uuid(特殊处理的id,64位8位数字符)
        chapterDto.setId(UuidUtil.getShortUuid());
        chapterService.save(chapterDto);
        return chapterDto;
    }
}
