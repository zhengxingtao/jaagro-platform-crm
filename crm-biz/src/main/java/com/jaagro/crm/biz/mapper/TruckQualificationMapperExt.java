package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckQualificationDto;
import com.jaagro.crm.api.dto.response.truck.ReturnTruckQualificationDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 */
public interface TruckQualificationMapperExt extends TruckQualificationMapper{

    /**
     * 查询待审核的运力资质列表
     */
    List<ReturnTruckQualificationDto> listByCriteria(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 待根据车队id获取审核运力资质列表
     *
     * @param criteriaDto
     * @return
     */
    List<ReturnTruckQualificationDto> listByIds(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 根据车辆id查询运力资质列表
     */
    List<ListTruckQualificationDto> listByTruckId(@Param("truckId") Integer truckId);

    /**
     * 根据司机id查询资质列表
     *
     * @param driverId
     * @return
     */
    List<ListTruckQualificationDto> listByDriverId(@Param("driverId") Integer driverId);

    /**
     * 根据id获取审核运力资质
     *
     * @param id
     * @return
     */
    ReturnTruckQualificationDto getById(Integer id);
}