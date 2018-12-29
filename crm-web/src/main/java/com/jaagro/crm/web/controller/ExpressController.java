package com.jaagro.crm.web.controller;

import com.jaagro.crm.api.dto.request.express.QueryExpressDto;
import com.jaagro.crm.api.dto.response.express.ExpressReturnDto;
import com.jaagro.crm.api.service.ExpressService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author gavin
 * @since 20181229
 */
@RestController
@Api(description = "智库直通车管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpressController {

    @Autowired
    private ExpressService expressService;


    /**
     * @param criteriaDto
     * @return
     * @Author gavin
     */
    @ApiOperation("智库直通车列表")
    @PostMapping("/express/listExpressByCriteria")
    public BaseResponse listExpressByCriteria(@RequestBody QueryExpressDto criteriaDto) {
        try {
            return BaseResponse.successInstance(expressService.listExpressByCriteria(criteriaDto));
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查询智库直通车失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "查看详情")
    @GetMapping("/express/{id}")
    public BaseResponse getNewsById(@PathVariable(value = "id") Integer id) {
        ExpressReturnDto returnDto = expressService.getExpressById(id);
        if (returnDto == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "id=" + id + "不存在");
        }
        return BaseResponse.successInstance(returnDto);
    }

}
