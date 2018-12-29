package com.jaagro.crm.biz.service.impl;

import com.jaagro.crm.api.constant.ContractType;
import com.jaagro.crm.api.dto.request.customer.CreateContractOilPriceDto;
import com.jaagro.crm.api.dto.response.contract.ReturnContractOilPriceDto;
import com.jaagro.crm.api.service.ContractOilPriceService;
import com.jaagro.crm.biz.entity.ContractOilPrice;
import com.jaagro.crm.biz.entity.CustomerContract;
import com.jaagro.crm.biz.mapper.ContractOilPriceMapperExt;
import com.jaagro.crm.biz.mapper.CustomerContractMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2018/12/26
 */
@Slf4j
@Service
public class ContractOilPriceServiceImpl implements ContractOilPriceService {

    @Autowired
    private ContractOilPriceMapperExt oilPriceMapperExt;
    @Autowired
    private CurrentUserService userService;
    @Autowired
    private CustomerContractMapperExt contractMapperExt;

    /**
     * 创建油价配制
     *
     * @param createContractOilPriceDto
     * @return
     */
    @Override
    public Boolean createOilPrice(CreateContractOilPriceDto createContractOilPriceDto) {
        Boolean flag = false;
        if (StringUtils.isEmpty(createContractOilPriceDto.getContractId())) {
            log.error("createOilPrice 创建油价配制合同id不能为空");
            return flag;
        }
        if (StringUtils.isEmpty(createContractOilPriceDto.getContractType())) {
            log.error("createOilPrice 创建油价配制合同类型不能为空");
            return flag;
        }
        if (StringUtils.isEmpty(createContractOilPriceDto.getEffectiveTime())) {
            log.error("createOilPrice 创建油价配制起始时间不能为空");
            return flag;
        }
        if (StringUtils.isEmpty(createContractOilPriceDto.getInvalidTime())) {
            log.error("createOilPrice 创建油价配制失效时间不能为空");
            return flag;
        }
        ContractOilPrice oilPrice = new ContractOilPrice();
        //查询是否存在历史记录
        CustomerContract customerContract = contractMapperExt.selectByPrimaryKey(createContractOilPriceDto.getContractId());
        List<ContractOilPrice> oilPriceList = oilPriceMapperExt.listByContractIdAndType(createContractOilPriceDto.getContractId(), createContractOilPriceDto.getContractType());
        if (!CollectionUtils.isEmpty(oilPriceList)) {
            for (ContractOilPrice price : oilPriceList) {
                //将已存在的记录设置为历史 && 截止日期设置为当前日期
                price
                        .setInvalidTime(new Date())
                        .setHistoryFlag(true)
                        .setModifyUserId(userService.getCurrentUser().getId())
                        .setModifyTime(new Date())
                        .setModifyUserName(userService.getCurrentUser().getName());
                oilPriceMapperExt.updateByPrimaryKeySelective(price);
            }
            //若有历史纪录 ==将新纪录的开始日期设置为当前日期
            oilPrice
                    .setEffectiveTime(new Date());
        } else {
            //若无历史纪录 ==将新纪录的开始日期设置为合同的生效日期
            oilPrice
                    .setEffectiveTime(customerContract.getStartDate());
        }
        BeanUtils.copyProperties(createContractOilPriceDto, oilPrice);
        if (customerContract == null) {
            log.error("createOilPrice 创建油价配制合同不存在");
            return flag;
        }
        if (createContractOilPriceDto != null) {
            oilPrice
                    .setContractType(ContractType.CUSTOMER)
                    .setInvalidTime(customerContract.getEndDate())
                    .setCreateUserId(userService.getCurrentUser().getId())
                    .setCreateTime(new Date())
                    .setCreateUserName(userService.getCurrentUser().getName());
            int result = oilPriceMapperExt.insertSelective(oilPrice);
            if (result > 0) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    /**
     * 根据合同id获取列表【不包括历史】
     *
     * @param contractId
     * @return
     */
    @Override
    public ReturnContractOilPriceDto getByContractId(Integer contractId, Integer contractType) {
        return oilPriceMapperExt.getByContractIdAndType(contractId, contractType);
    }

}