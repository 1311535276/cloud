package com.accp.business.controller.admin;

import com.accp.business.config.BusinessApplication;
import com.accp.server.domain.Chapter;
import com.accp.server.dto.ChapterDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.ChapterService;
import com.accp.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseDto list(@RequestBody PageDto pageDto){
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto=new ResponseDto();
    //存储结果: 可看底层代码
    responseDto.setContent(pageDto);
    chapterService.list(pageDto);
    return responseDto;
    }
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    /**
     * 保存，id有值时更新，无值时新增
     */
    @RequestMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto=new ResponseDto();
        //给chapter表的id 存储Uuid(特殊处理的id,64位8位数字符)
//        chapterDto.setId(UuidUtil.getShortUuid());
        LOG.info("chapterDto:{}",chapterDto);
        //存储结果: 可看底层代码
        responseDto.setContent(chapterDto);
        chapterService.save(chapterDto);
        return responseDto;
    }
}
