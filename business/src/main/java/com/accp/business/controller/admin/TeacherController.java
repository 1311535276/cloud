package com.accp.business.controller.admin;

import com.accp.server.dto.CategoryDto;
import com.accp.server.dto.TeacherDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.accp.server.util.ValidatorUtil;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;
    public static final String BUSINESS_NAME = "讲师";

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto = new ResponseDto();
        //存储结果: 可看底层代码
        responseDto.setContent(pageDto);
        teacherService.list(pageDto);
        return responseDto;
    }

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);

    /**
     * 增+修，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody TeacherDto teacherDto) {
        //保存校验
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto = new ResponseDto();
        //给teacher表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("teacherDto:{}", teacherDto);

        teacherService.save(teacherDto);
        //存储结果: 可看底层代码
        responseDto.setContent(teacherDto);
        return responseDto;
    }

    /**
     * 删 传入id
     *
     * @PathVariable 接收请求路径中占位符的值
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto = new ResponseDto();
        //给teacher表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("id:{}", id);

        teacherService.delete(id);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<TeacherDto> teacherDtoList = teacherService.all();
        responseDto.setContent(teacherDtoList);
        return responseDto;
    }
}
