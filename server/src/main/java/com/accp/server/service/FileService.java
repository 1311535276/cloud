package com.accp.server.service;

import com.accp.server.domain.File;
import com.accp.server.domain.FileExample;
import com.accp.server.dto.FileDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.FileMapper;
import com.accp.server.util.CopyUtil;
import com.accp.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
@Service
public class FileService {

private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    @Resource
    public FileMapper fileMapper;
    /**
    *列表查询
    */
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        FileExample fileExample = new FileExample();
        //查询
        List<File> fileList=fileMapper.selectByExample(fileExample);
       //PageHelper内置的方法 pageinfo
        PageInfo<File> pageInfo=new PageInfo<>(fileList);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtoList=new ArrayList<FileDto>();
        for (int i = 0,l =fileList.size();i<l; i++) {
            File file = fileList.get(i);
            FileDto fileDto = new FileDto();
            BeanUtils.copyProperties(file,fileDto);
            fileDtoList.add(fileDto);
        }
        pageDto.setList(fileDtoList);
    }

    /**
     * save:增 改
     */
    public void save(FileDto fileDto){
        File file= CopyUtil.copy(fileDto,File.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(fileDto.getId())){
         LOG.info("进入新增:{}",fileDto);
         this.insert(file);
     }else{
         LOG.info("进入修改:{}",fileDto);
         //不为空id 就是修改 进入修改方法
        this.update(file);
     }
    }
    /**
     * 新增
     */
    private void insert(File  file ){
                Date now =new Date();

        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
                file.setCreatedAt(now);
                file.setUpdatedAt(now);
        file.setId(UuidUtil.getShortUuid());
//        File file = new File();
//        BeanUtils.copyProperties(fileDto,file);
        fileMapper.insert(file);
    }
    /**
     *  修改
     */
    private void update(File  file ){
        //修改时间
            file.setUpdatedAt(new Date());
        fileMapper.updateByPrimaryKey(file);

    }

    /**
     * 删除
     */
    public void delete(String id){
    fileMapper.deleteByPrimaryKey(id);
    }

}
