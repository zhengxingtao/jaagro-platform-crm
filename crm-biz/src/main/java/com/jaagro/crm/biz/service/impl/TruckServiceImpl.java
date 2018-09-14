package com.jaagro.crm.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.crm.api.constant.AuditStatus;
import com.jaagro.crm.api.dto.request.truck.CreateDriverDto;
import com.jaagro.crm.api.dto.request.truck.CreateListTruckQualificationDto;
import com.jaagro.crm.api.dto.request.truck.CreateTruckDto;
import com.jaagro.crm.api.dto.request.truck.ListTruckCriteriaDto;
import com.jaagro.crm.api.dto.response.truck.*;
import com.jaagro.crm.api.service.DriverClientService;
import com.jaagro.crm.api.service.OssSignUrlClientService;
import com.jaagro.crm.api.service.TruckQualificationService;
import com.jaagro.crm.api.service.TruckService;
import com.jaagro.crm.biz.entity.Truck;
import com.jaagro.crm.biz.mapper.TruckMapper;
import com.jaagro.crm.biz.mapper.TruckQualificationMapper;
import com.jaagro.crm.biz.mapper.TruckTypeMapper;
import com.jaagro.utils.ResponseStatusCode;
import com.jaagro.utils.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liqiangping
 */
@Service
public class TruckServiceImpl implements TruckService {

    private static final Logger log = LoggerFactory.getLogger(TruckServiceImpl.class);

    @Autowired
    private TruckMapper truckMapper;
    @Autowired
    private DriverClientService driverClientService;
    @Autowired
    private TruckQualificationService truckQualificationService;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private TruckTypeMapper truckTypeMapper;
    @Autowired
    private TruckQualificationMapper truckQualificationMapper;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;

    private void changeUrl(List<ListTruckQualificationDto> driverQualificationList){
        for (ListTruckQualificationDto dto : driverQualificationList
        ) {
            //替换资质证照地址
            String[] strArray = {dto.getCertificateImageUrl()};
            List<URL> urlList = ossSignUrlClientService.listSignedUrl(strArray);
            dto.setCertificateImageUrl(urlList.get(0).toString());
        }
    }

    /**
     * 获取单条
     * @param truckId
     * @return
     */
    @Override
    public Map<String, Object> getTruckById(Integer truckId) {
        Truck truck = this.truckMapper.selectByPrimaryKey(truckId);
        if (truck == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_EMPTY.getCode(), "车辆不存在");
        }
        GetTruckDto result = truckMapper.getTruckById(truckId);
        if (StringUtils.isEmpty(result)) {
            return ServiceResult.error(ResponseStatusCode.FORBIDDEN_ERROR.getCode(), truckId + ": 无效");
        }
        //替换车辆资质证地址
        if (result.getQualificationDtoList().size() > 0) {
            changeUrl(result.getQualificationDtoList());
        }
        List<DriverReturnDto> drivers = driverClientService.listByTruckId(truckId);
        if (drivers.size() > 0) {
            //填充司机资质证照列表
            for (DriverReturnDto driverReturnDto : drivers) {
                List<ListTruckQualificationDto> driverQualificationList = this.truckQualificationMapper.listByDriverId(driverReturnDto.getId());
                if (driverQualificationList.size() > 0) {
                    changeUrl(driverQualificationList);
                }
                driverReturnDto.setDriverQualificationList(driverQualificationList);
            }
        }
        result
                .setDrivers(drivers)
                .setTruckTypeId(this.truckTypeMapper.getById(truck.getTruckTypeId()));
        return ServiceResult.toResult(result);
    }

    /**
     * 获取单条车辆 返回对象
     *
     * @param truckId
     * @return
     */
    @Override
    public GetTruckDto getTruckByIdReturnObject(Integer truckId) {
        Truck truck = this.truckMapper.selectByPrimaryKey(truckId);
        GetTruckDto result = truckMapper.getTruckById(truckId);
        List<DriverReturnDto> drivers = driverClientService.listByTruckId(truckId);
        result
                .setDrivers(drivers)
                .setTruckTypeId(this.truckTypeMapper.getById(truck.getTruckTypeId()));
        return result;
    }

    /**
     * 创建车辆对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createTruck(CreateTruckDto truckDto) {
        Truck truck = new Truck();
        BeanUtils.copyProperties(truckDto, truck);
        truck.setCreateUserId(this.currentUserService.getCurrentUser().getId());
        truckMapper.insertSelective(truck);

        //车辆资质列表
        if (truckDto.getTruckQualifications() != null && truckDto.getTruckQualifications().size() > 0) {
            CreateListTruckQualificationDto listTruckQualification = new CreateListTruckQualificationDto();
            listTruckQualification
                    .setTruckTeamId(truck.getTruckTeamId())
                    .setTruckId(truck.getId());
            listTruckQualification.setQualification(truckDto.getTruckQualifications());
            truckQualificationService.createTruckQualification(listTruckQualification);
        }

        //司机列表
        if (truckDto.getDriver() != null && truckDto.getDriver().size() > 0) {
            for (CreateDriverDto driverDto : truckDto.getDriver()) {
                Integer driverId = 0;
                driverDto
                        .setTruckId(truck.getId())
                        .setTruckTeamId(truck.getTruckTeamId());
                try {
                    driverId = driverClientService.createDriverReturnId(driverDto);
                } catch (RuntimeException e) {
                    log.error("司机新建失败：" + e.getMessage());
                    throw e;
                }

                //司机资质列表
                if (driverDto.getDriverQualifications() != null && driverDto.getDriverQualifications().size() > 0) {
                    CreateListTruckQualificationDto listTruckQualification = new CreateListTruckQualificationDto();
                    listTruckQualification
                            .setTruckTeamId(truck.getTruckTeamId())
                            .setTruckId(truck.getId())
                            .setDriverId(driverId);
                    listTruckQualification.setQualification(driverDto.getDriverQualifications());
                    truckQualificationService.createTruckQualification(listTruckQualification);
                }
            }
        }
        return ServiceResult.toResult(truck.getTruckTeamId());
    }

    /**
     * 修改车辆
     *
     * @param truckDto
     * @return
     */
    @Override
    public Map<String, Object> updateTruck(CreateTruckDto truckDto) {
        if (truckMapper.selectByPrimaryKey(truckDto.getTruckTeamId()) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), truckDto.getId() + " ：id不正确");
        }
        Truck truck = new Truck();
        BeanUtils.copyProperties(truckDto, truck);
        truck
                .setModifyUserId(currentUserService.getCurrentUser().getId())
                .setModifyTime(new Date());
        truckMapper.updateByPrimaryKeySelective(truck);
        return ServiceResult.toResult(truckMapper.getTruckById(truckDto.getId()));
    }

    /**
     * 删除车辆
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteTruck(Integer id) {
        if (truckMapper.selectByPrimaryKey(id) == null) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), id + " ：id不正确");
        }
        //删除车辆所属的司机
        driverClientService.deleteDriverByTruckId(id);
        //删除车辆
        truckMapper.deleteTruckLogic(AuditStatus.STOP_COOPERATION, id);
        return ServiceResult.toResult("删除成功");
    }

    @Override
    public Map<String, Object> listTruck(ListTruckCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        List<GetTruckDto> result = truckMapper.listTruckByTeamId(criteria.getTruckTeamId(), criteria.getTruckNumber());
        if (result == null || result.size() == 0) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查无数据");
        }
        return ServiceResult.toResult(new PageInfo<>(result));
    }

    @Override
    public Map<String, Object> listTruckByCriteria(ListTruckCriteriaDto criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        List<ListTruckDto> result = truckMapper.listTruckByCriteria(criteria);
        if (result == null || result.size() == 0) {
            return ServiceResult.error(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "查无数据");
        }
        return ServiceResult.toResult(new PageInfo<>(result));
    }

    @Override
    public List<ListTruckTypeDto> listTruckType() {
        return truckTypeMapper.listAll();
    }

    /**
     * 获取单条车辆类型
     * @param id
     * @return
     */
    @Override
    public ListTruckTypeDto getTruckTypeById(Integer id) {
        return truckTypeMapper.getById(id);
    }

    /**
     * @Author gavin
     * @param criteriaDto
     * @return
     */
    @Override
    public List<ListTruckWithDriversDto> listTrucksWithDrivers(ListTruckCriteriaDto criteriaDto) {
        List<ListTruckWithDriversDto> result = new ArrayList<>();
        PageHelper.startPage(criteriaDto.getPageNum(), criteriaDto.getPageSize());
        List<ListTruckDto> truckList = truckMapper.listTruckByCriteria(criteriaDto);
        if (!CollectionUtils.isEmpty(truckList)) {
            for (ListTruckDto listTruckDto : truckList) {
                ListTruckWithDriversDto truckWithDriversDto = new ListTruckWithDriversDto();
                List<DriverReturnDto> drivers = driverClientService.listByTruckId(listTruckDto.getTruckId());
                truckWithDriversDto.setListTruckDto(listTruckDto);
                truckWithDriversDto.setDrivers(drivers);
                result.add(truckWithDriversDto);
            }
        }
        return result;
    }
}