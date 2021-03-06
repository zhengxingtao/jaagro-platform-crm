package com.jaagro.crm.api.service;

import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckQualificationCriteriaDto;
import com.jaagro.crm.api.dto.request.truck.UpdateTruckQualificationDto;
import com.jaagro.crm.api.dto.response.truck.ListTruckQualificationDto;

import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
public interface TruckQualificationService {

    /**
     * 创建车队资质
     *
     * @param dto
     * @return
     */
    Map<String, Object> createTruckQualification(CreateListTruckQualificationDto dto);

    /**
     * 修改多张
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateQualification(List<UpdateTruckQualificationDto> dto);

    /**
     * 修改单张
     *
     * @param dto
     * @return
     */
    Map<String, Object> updateQualificationCertific(UpdateTruckQualificationDto dto);

    /**
     * 逻辑删除 根据图片id数组
     *
     * @param ids
     * @return
     */
    Map<String, Object> deleteQualification(Integer[] ids);

    /**
     * 逻辑删除 根据司机id
     *
     * @param ids
     * @return
     */
    Map<String, Object> deleteQualificationByDriverId(Integer driverId);

    /**
     * 分页查询待审核的运力资质
     *
     * @return
     */
    Map<String, Object> listQualification(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 分页查询运力资质根据车队id
     *
     * @return
     */
    Map<String, Object> listQualificationByTruckIds(ListTruckQualificationCriteriaDto criteriaDto);

    /**
     * 根据司机id获取资质列表
     *
     * @param driverId
     * @return
     */
    List<ListTruckQualificationDto> listQualificationByDriverId(Integer driverId);
}
