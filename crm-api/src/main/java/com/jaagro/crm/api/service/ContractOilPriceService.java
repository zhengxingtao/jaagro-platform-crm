package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractOilPriceDto;

/**
 * @author baiyiran
 * @Date 2018/12/25
 */
public interface ContractOilPriceService {

    /**
     * 创建油价配制
     *
     * @param createContractOilPriceDto
     * @return
     */
    Boolean createOilPrice(CreateContractOilPriceDto createContractOilPriceDto);

    /**
     * 根据合同id获取列表【不包括历史】
     *
     * @param contractId
     * @param contractType
     * @return
     */
    ReturnContractOilPriceDto getByContractId(Integer contractId, Integer contractType);

    /**
     * 根据合同id和合同类型删除
     * @param contractId 合同id
     * @param contractType 合同类型
     * @return
     */
    Integer disableByContractIdAndType(Integer contractId, Integer contractType);
}
