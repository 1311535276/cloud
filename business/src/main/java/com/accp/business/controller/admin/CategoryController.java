package com.accp.business.controller.admin;

import com.accp.server.dto.CategoryDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.accp.server.util.ValidatorUtil;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    public static final String BUSINESS_NAME = "分类";

    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<CategoryDto> categoryDtoList = categoryService.all();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto = new ResponseDto();
        //存储结果: 可看底层代码
        responseDto.setContent(pageDto);
        categoryService.list(pageDto);
        return responseDto;
    }

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    /**
     * 增+修，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto) {
        //保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto = new ResponseDto();
        //给category表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("categoryDto:{}", categoryDto);

        categoryService.save(categoryDto);
        //存储结果: 可看底层代码
        responseDto.setContent(categoryDto);
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
        //给category表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("id:{}", id);

        categoryService.delete(id);
        return responseDto;
    }
}
