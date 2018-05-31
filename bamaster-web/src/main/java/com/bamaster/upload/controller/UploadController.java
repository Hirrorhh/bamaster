package com.bamaster.upload.controller;

import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.Result;
import com.bamaster.core.utils.TokenManagerUtils;
import com.bamaster.core.utils.UploadUtil;
import com.bamaster.wf.dao.CusResourceMapper;
import com.bamaster.wf.pojo.CusResource;
import com.bamaster.wf.service.CusResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.swing.text.AbstractDocument;
import java.io.File;
import java.util.*;


/**
 * 文件上传的控制器
 * <p>
 * Created by hirror on 2017/9/20.
 */
@Controller
@Api(value = "上传文件控制器", description = "上传文件控制器")
public class UploadController {

    @Resource
    CusResourceService resourceService;

    @RequestMapping("/uploadFile")
    @ResponseBody
    @ApiOperation(value = "为节点分配操作人员", notes = "为节点分配操作人员", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "description", value = "上传文件描述", required = true, paramType = "query"),
            @ApiImplicitParam(name = "nodeid", value = "节点id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "cusResourceId", value = "客户资料id(修改时填写)", paramType = "query")
    })

    public Result uploadFile(MultipartFile file,String description,@RequestParam(required=true)Integer nodeid,Integer cusResourceId) throws Exception {
        try {
            if (file ==null){
                return Result.error("上传文件错误!");
            }
            CusResource cusResource = new CusResource();
            String realName = file.getOriginalFilename();


            LoggerUtils.debug(getClass(),"开始上传文件:" + realName);
            String uuidname = UploadUtil.generateUUIDFileName(realName);
            String randompath = UploadUtil.generateRandomDir(uuidname);
            ResourceBundle resource = ResourceBundle.getBundle("upload");
            String baseUrl = resource.getString("upload_file_base_url");
            String savepath = baseUrl + randompath;

            File path = new File(savepath);
            path.mkdirs();
            File upFile = new File(path, uuidname);
            file.transferTo(upFile);
            //上传结束


            cusResource.setRealname(realName);
            cusResource.setUuidname(uuidname);
            cusResource.setDescription(description);
            cusResource.setSavepath(savepath+"/"+uuidname);
            cusResource.setUploadtime(new Date());
            cusResource.setUserid(TokenManagerUtils.getUserId());
            cusResource.setNodeid(nodeid);
            if(null==cusResourceId){
                resourceService.saveResourceDetail(cusResource);
            }else {
                cusResource.setId(cusResourceId);
                resourceService.updateResourceDetail(cusResource);
            }
            return Result.ok().put("url",savepath);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "uploadFile出错", e);
            return Result.error();

        }
    }

   /* @RequestMapping("/upload/uploadPics.do")
    @ResponseBody
    public List<String> uploadFiles(@RequestParam MultipartFile[] files) throws Exception {

        List<String> pathList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                logger.info("多文件上传-文件的名称为" + file.getOriginalFilename());
                //将文件上传到分布式系统中并且返回文件的存储路径和名称
                String uploadFiles = FastDFSUtils.uploadFile(file.getBytes(), file.getOriginalFilename());
                //将返回后的文件地址存放到list中返回
                pathList.add(uploadFiles);
            }
        }
        logger.info("多文件上传成功的文件个数: " + pathList.size());
        return pathList;
    }

    @RequestMapping("/upload/uploadFck.do")
    @ResponseBody
    public HashMap<String, Object> uploadFck(HttpServletRequest request, HttpServletResponse response) throws
            Exception {
        //将request转换成MultipartRequest
        MultipartRequest multipartRequest = (MultipartRequest) request;

        //使用multipartRequest的API获得上传的所有文件列表
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        //定义map存放返回的数据
        HashMap<String, Object> resultMap = new HashMap<>();

        //遍历文件的map
        Set<Entry<String, MultipartFile>> entries = fileMap.entrySet();
        for (Entry<String, MultipartFile> entry : entries) {
            MultipartFile file = entry.getValue();
            logger.info("上传文件的文件名: " + file.getOriginalFilename());
            String filePath = FastDFSUtils.uploadFile(file.getBytes(), file.getOriginalFilename());

            resultMap.put("error", 0);
            resultMap.put("url", filePath);
            return resultMap;
        }
        return null;
    }*/
}
