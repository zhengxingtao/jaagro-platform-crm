package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.customer.ListCustomerQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.customer.CustomerQualificationReturnDto;
import com.jaagro.crm.biz.entity.CustomerQualification;

import java.util.List;

public interface CustomerQualificationMapper {
    /**
     *
     * @mbggenerated 2018-08-23
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insert(CustomerQualification record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int insertSelective(CustomerQualification record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    CustomerQualification selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKeySelective(CustomerQualification record);

    /**
     *
     * @mbggenerated 2018-08-23
     */
    int updateByPrimaryKey(CustomerQualification record);

    /**
     * 查询客户Id查询资质证明
     *
     * @param id
     * @return
     */
    List<CustomerQualificationReturnDto> getByCustomerQualificationId(Integer id);

    /**
     * 分页查询
     *
     * @param certificDto
     * @return
     */
    List<CustomerQualificationReturnDto> getByQualificationCriteriDto(ListCustomerQualificationCriteriaDto certificDto);
}