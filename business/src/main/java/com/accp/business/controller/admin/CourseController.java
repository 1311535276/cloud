package com.accp.business.controller.admin;

import com.accp.server.dto.CourseDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.accp.server.util.ValidatorUtil;
import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/course")
public class CourseController {

    @Resource
    private CourseService courseService;
public static final String BUSINESS_NAME="课程";
@PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto=new ResponseDto();
    //存储结果: 可看底层代码
    responseDto.setContent(pageDto);
    courseService.list(pageDto);
    return responseDto;
    }
    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    /**
     * 增+修，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto){
    //保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto=new ResponseDto();
        //给course表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("courseDto:{}",courseDto);

        courseService.save(courseDto);
        //存储结果: 可看底层代码
        responseDto.setContent(courseDto);
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
        //给course表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("id:{}",id);

        courseService.delete(id);
        return responseDto;
    }
}