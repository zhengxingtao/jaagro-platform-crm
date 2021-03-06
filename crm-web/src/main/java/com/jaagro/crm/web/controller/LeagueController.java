package com.jaagro.crm.web.controller;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.league.CreateLeagueDto;
import com.jaagro.crm.api.dto.request.league.ListLeagueCerteriaDto;
import com.jaagro.crm.api.dto.response.league.ListLeagueDto;
import com.jaagro.crm.api.service.LeagueService;
import com.jaagro.crm.web.vo.league.ListLeagueVo;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 销售机会管理
 *
 * @author baiyiran
 * @Date 2018/11/14
 */
@RestController
@Slf4j
@Api(value = "league", description = "销售机会管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @ApiOperation(value = "新增销售机会")
    @PostMapping(value = "/league", consumes = "application/json")
    public BaseResponse insertLeague(@RequestBody CreateLeagueDto createLeagueDto) {
        if (StringUtils.isEmpty(createLeagueDto.getLeagueType())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "销售机会类型不能为空");
        }
        if (StringUtils.isEmpty(createLeagueDto.getLeagueName())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "姓名不能为空");
        }
        if (StringUtils.isEmpty(createLeagueDto.getPhone())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "手机号不能为空");
        }
        Map<String, Object> result;
        try {
            result = leagueService.createLeague(createLeagueDto);
        } catch (Exception ex) {
            log.error("录入销售机会:{}", ex.getMessage());
            return BaseResponse.errorInstance(ex.getMessage());
        }
        return BaseResponse.service(result);
    }

    @ApiOperation(value = "分页查询销售机会")
    @PostMapping("/listLeagueByCriteria")
    public BaseResponse listLeagueByCriteria(@RequestBody ListLeagueCerteriaDto certeriaDto) {
        if (StringUtils.isEmpty(certeriaDto.getPageNum())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageNum不能为空");
        }
        if (StringUtils.isEmpty(certeriaDto.getPageSize())) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "pageSize不能为空");
        }
        PageInfo pageInfo = leagueService.listLeagueByCriteria(certeriaDto);
        List<ListLeagueDto> leagueDtoList = pageInfo.getList();
        if (!CollectionUtils.isEmpty(leagueDtoList)) {
            List<ListLeagueVo> leagueVoList = new ArrayList<>();
            for (ListLeagueDto leagueDto : leagueDtoList) {
                ListLeagueVo leagueVo = new ListLeagueVo();
                BeanUtils.copyProperties(leagueDto, leagueVo);
                leagueVoList.add(leagueVo);
            }
            pageInfo.setList(leagueVoList);
        }
        return BaseResponse.successInstance(pageInfo);
    }

}
