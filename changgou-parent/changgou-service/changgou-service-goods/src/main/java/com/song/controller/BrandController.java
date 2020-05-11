package com.song.controller;

import com.song.entity.Constant;
import com.song.entity.Result;
import com.changgou.goods.pojo.Brand;
import com.song.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Api(value = "brand表的接口",description = "brand业务接口")
@CrossOrigin
@Slf4j
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/brand")
    @ApiOperation(value = "插入接口",httpMethod = "POST")
    public Result insert(@RequestBody Brand brand){
        return brandService.insert(brand);
    }

    @GetMapping("/brand/{id}")
    @ApiOperation(value = "通过id查询的接口",httpMethod = "GET")
    public Result queryById(@PathVariable("id") Integer id , HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(Constant.token.TOKEN_AUTHOR);

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        System.out.println("--------------"+(request==httpServletRequest) +"token: "+token);
        return brandService.queryById(id);
    }

    @PostMapping("/brand/all")
    @ApiOperation(value = "通过条件查询的接口",httpMethod = "POST")
    public Result queryAll(@RequestBody Brand brand){
        return brandService.queryAll(brand);
    }
    @GetMapping("/brand/{page}/{pageSize}")
    public Result queryPage(@PathVariable("page")Integer page,@PathVariable("pageSize")Integer pageSize){
        return brandService.queryPage(page,pageSize);
    }
    @DeleteMapping("/brand/{id}")
    public Result delete(@PathVariable("id")Integer id){
        return brandService.delete(id);
    }

    @PutMapping("/brand/{id}")
    public Result update(@PathVariable("id") Integer id,@RequestBody Brand brand){
        return brandService.update(id,brand);
    }

    @PostMapping("/brand/condition/{page}/{pageSize}")
    @ApiOperation(value = "分页条件查询",httpMethod = "POST")
    public Result queryPageByCondition(@RequestBody Brand brand,@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pagesize){
        return brandService.queryPageByCondition(page,pagesize,brand);
    }
    @PostMapping("/upload/file")
    public String uploadFIle(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String filenameExtension = StringUtils.getFilenameExtension(originalFilename);
        log.info("路径：{}  ，{}",originalFilename,filenameExtension);
        InputStream inputStream = multipartFile.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("E:/" + originalFilename));
        byte[] bytes = new byte[1024];
        int i;
        while ((i=bufferedInputStream.read(bytes))!=-1){
            bufferedOutputStream.write(bytes,0,i);
        }
        bufferedOutputStream.flush();
        inputStream.close();
        bufferedInputStream.close();
        bufferedOutputStream.close();
        return "success";
    }

    @GetMapping("/getimage/{height}/{width}")
    public String getImage(HttpServletResponse response,@PathVariable("height") Integer height, @PathVariable("width") Integer width ) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("E:/P81105-145646.jpg");
        BufferedImage read = ImageIO.read(fileInputStream);
        BufferedImage bufferedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_BGR);
        bufferedImage.getGraphics().drawImage(read.getScaledInstance(width,height, Image.SCALE_SMOOTH),0,0,null);
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
        return "success";
    }
}
