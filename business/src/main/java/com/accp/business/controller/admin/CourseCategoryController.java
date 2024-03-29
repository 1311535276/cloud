package com.accp.business.controller.admin;

import com.accp.server.dto.CourseCategoryDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.CourseCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.accp.server.util.ValidatorUtil;
import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/courseCategory")
public class CourseCategoryController {

    @Resource
    private CourseCategoryService courseCategoryService;
public static final String BUSINESS_NAME="课程分类";
@PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto=new ResponseDto();
    //存储结果: 可看底层代码
    responseDto.setContent(pageDto);
    courseCategoryService.list(pageDto);
    return responseDto;
    }
    private static final Logger LOG = LoggerFactory.getLogger(CourseCategoryController.class);
    /**
     * 增+修，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseCategoryDto courseCategoryDto){
    //保存校验
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto=new ResponseDto();
        //给courseCategory表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("courseCategoryDto:{}",courseCategoryDto);

        courseCategoryService.save(courseCategoryDto);
        //存储结果: 可看底层代码
        responseDto.setContent(courseCategoryDto);
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
        //给courseCategory表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("id:{}",id);

        courseCategoryService.delete(id);
        return responseDto;
    }
}
