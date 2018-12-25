package com.jaagro.crm.biz.mapper;

import com.jaagro.crm.api.dto.request.contract.SiteDto;
import com.jaagro.crm.biz.entity.SettleMileage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 结算里程配制CRUD扩展
 * @author yj
 * @since 2018/12/24
 */
public interface SettleMileageMapperExt extends SettleMileageMapper {
    List<SettleMileage> getSettleMileageList(@Param("customerContractId") Integer customerContractId,@Param("siteDtoList") List<SiteDto> siteDtoList);
}
