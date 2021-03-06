package com.shuheng.datacenter.api;

import com.shuheng.datacenter.base.BaseApi;
import com.shuheng.datacenter.entity.ApiConstants;
import com.shuheng.datacenter.entity.ApiData;
import com.shuheng.datacenter.entity.PageData;
import com.shuheng.datacenter.server.AppUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * app用户
 */
@RestController
public class AppUserApi extends BaseApi{

    @Resource(name = "appUserService")
    private AppUserService appUserService;

    /**
     * 人员列表
     * @param timestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/userList")
    public ApiData userList(String timestamp,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        if(StringUtils.isNotBlank(timestamp)){
            pd.put("timestamp",timestamp);
        }

        try {
            List<PageData> list = appUserService.listUser(pd);
            data.setDatas(list);
            data.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            data.setErrorCode(ApiConstants.CODE_202);
            return data;
        }
        return data;
    }

    /**
     * 人员新增列表
     * @param date  当日时间  必填
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/userAddList")
    public ApiData userAddList(String date,String pageNumber,String pageSize){
        ApiData data = new ApiData();
        PageData pd = this.getPageInfo(pageNumber,pageSize);
        //timestamp为空，返回参数错误
        if(StringUtils.isBlank(date)){
            data.setErrorCode(ApiConstants.CODE_201);
            return data;
        }
        pd.put("date",date);

        try {
            List<PageData> list = appUserService.listAddUser(pd);
            data.setDatas(list);
            data.setErrorCode(ApiConstants.CODE_200);
        } catch (Exception e) {
            e.printStackTrace();
            data.setErrorCode(ApiConstants.CODE_202);
            return data;
        }
        return data;
    }

}
