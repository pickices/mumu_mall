package com.liuxinchi.mumu_mall.controller;

import com.github.pagehelper.PageInfo;
import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import com.liuxinchi.mumu_mall.common.Constant;
import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.request.AddProductReq;
import com.liuxinchi.mumu_mall.model.request.UpdateProductReq;
import com.liuxinchi.mumu_mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Controller
public class ProductAdminController {

    @Autowired
    ProductService productService;

    @ResponseBody
    @PostMapping("/admin/product/add")
    public ApiRestResponse addProduct(@RequestBody @Valid AddProductReq addProductReq) throws MumuMallException {
        productService.addProduct(addProductReq);
        return ApiRestResponse.success();
    }

    @ResponseBody
    @PostMapping("/admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));

        UUID uuid = UUID.randomUUID();
        String newFilename = uuid.toString()+suffixName;

        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        File destFile = new File(Constant.FILE_UPLOAD_DIR+newFilename);

        if(!fileDirectory.exists()){
            if(!fileDirectory.mkdir()){
                ApiRestResponse.error(MumuMallExceptionEnum.MKDIR_FAILED);
            }
        }

        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL()+""))+"/upload/"+newFilename);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(MumuMallExceptionEnum.UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri){

        URI effectiveURI;

        try {
            effectiveURI =  new URI(uri.getScheme(),uri.getUserInfo(),uri.getHost(),uri.getPort(),null,null,null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        
        return effectiveURI;
    }

    @ResponseBody
    @PostMapping("/admin/product/update")
    public ApiRestResponse updateProduct(@RequestBody @Valid UpdateProductReq updateProductReq) throws MumuMallException {
        productService.updateProduct(updateProductReq);
        return ApiRestResponse.success();
    }

    @ResponseBody
    @PostMapping("/admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam("id") Integer id) throws MumuMallException {
        productService.deleteProduct(id);
        return ApiRestResponse.success();
    }

    @ResponseBody
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam("ids") Integer[] ids, @RequestParam("sellStatus") Integer sellStatus) throws MumuMallException {
        productService.batchUpdateSellStatus(ids,sellStatus);
        return ApiRestResponse.success();
    }

    @ResponseBody
    @PostMapping("/admin/product/list")
    public ApiRestResponse listForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
