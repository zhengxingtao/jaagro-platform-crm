package com.jaagro.crm.api.service;

import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.dto.request.contract.DriverContractSettleCondition;
import com.jaagro.crm.api.dto.request.truck.CreateTruckTeamContractDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckTeamContractCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckTeamContractDto;
import com.jaagro.crm.api.dto.response.truck.ListDriverContractSettleDto;
import com.jaagro.crm.api.dto.response.truck.ListDriverContractSettlelInfoDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckTeamContractService {

    /**
     * 创建车队合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamContract(CreateTruckTeamContractDto dto);

    /**
     * 获取单条合同
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Integer id);

    /**
     * 根据合同编号查看合同
     */
    Map<String, Object> getByContractNumber(String contractNumber);

    /**
     * 修改车队合同
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateTruckTeamContract(UpdateTruckTeamContractDto dto);

    /**
     * 创建车队合同关联关系
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckTeamContracts(List<CreateTruckTeamContractDto> dto, Integer truckTeamId);

    /**
     * 分页查询车队合同
     *
     * @param criteriaDto
     * @return
     */
    Map<String, Object> listByCriteria(ListTruckTeamContractCriteriaDto criteriaDto);

    /**
     * 逻辑删除
     *
     * @return
     */
    Map<String, Object> disableContract(Integer id);

    /**
     * 创建车队合同报价
     */
    void createTruckTeamContractPrice(CreateTruckTeamContractDto dto, Integer userId, Integer contractId);

    /**
     * 运力合同报价列表
     *
     * @param condition
     * @return
     */
    List<ListDriverContractSettlelInfoDto> listTruckTeamContractPrice(DriverContractSettleCondition condition);

    /**
     * 运力合同报价详情
     *
     * @param condition
     * @return
     */
    List<ListDriverContractSettleDto> listTruckTeamContractPriceDetails(DriverContractSettleCondition condition);

    /**
     * 当前车辆类型所有的报价历史记录
     *
     * @param condition
     * @return
     */
    PageInfo<ListDriverContractSettleDto> listTruckTeamContractPriceHistoryDetails(DriverContractSettleCondition condition);
}
