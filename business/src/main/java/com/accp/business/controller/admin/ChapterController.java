package com.accp.business.controller.admin;

import com.accp.server.dto.ChapterDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
//日志输出sql语句
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    @Resource
    private ChapterService chapterService;

@PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto=new ResponseDto();
    //存储结果: 可看底层代码
    responseDto.setContent(pageDto);
    chapterService.list(pageDto);
    return responseDto;
    }

    /**
     * 增+修，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto=new ResponseDto();
        //给chapter表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("chapterDto:{}",chapterDto);

        chapterService.save(chapterDto);
        //存储结果: 可看底层代码
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    /**
     * 删 传入id
     * @PathVariable 接收请求路径中占位符的值
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto=new ResponseDto();
        //给chapter表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("id:{}",id);

        chapterService.delete(id);
        return responseDto;
    }
}
