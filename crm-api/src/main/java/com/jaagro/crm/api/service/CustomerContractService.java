package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.customer.*;
import com.jaagro.crm.api.dto.request.customer.CreateCustomerContractDto;
import com.jaagro.crm.api.dto.response.customer.CustomerContractReturnDto;

import java.util.List;
import java.util.Map;

/**
 * dto请参照customer
 *
 * @author baiyiran
 */
public interface CustomerContractService {

    /**
     * 新增单个客户联系人，注意新增CreateDto
     *
     * @param dto
     * @return
     */
    Map<String, Object> createCustomerContract(CreateCustomerContractDto dto);

    /**
     * 新增客户联系人列表
     *
     * @param dtos
     * @return
     */
    Map<String, Object> createCustomerContract(List<CreateCustomerContractDto> dtos,Long CustomerId);

    /**
     * 修改单个客户联系人，注意新增updateDto
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateCustomerContract(UpdateCustomerContractDto dto);

    /**
     * 修改客户联系人列表
     *
     * @param dtos
     * @return
     */
    Map<String, Object> updateCustomerContract(List<UpdateCustomerContractDto> dtos);

    /**
     * 获取单条记录
     *
     * @param id
     * @return
     */
    Map<String, Object> getById(Long id);

    /**
     * 根据条件分页获取
     *
     * @param dto
     * @return
     */
    Map<String, Object> listByCriteria(ListCustomerContractCriteriaDto dto);

    /**
     * 删除客户联系人，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableCustomerContract(Long id);

    /**
     * 删除客户联系人，注意逻辑删除
     *
     * @param id
     * @return
     */
    Map<String, Object> disableCustomerContract(List<CustomerContractReturnDto> customerContractReturnDtos);
}
