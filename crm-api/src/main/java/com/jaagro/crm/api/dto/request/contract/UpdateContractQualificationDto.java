package com.jaagro.crm.api.dto.request.contract;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class UpdateContractQualificationDto implements Serializable {

    /**
     * 合同资质证照主键id
     */
    private Integer id;

    /**
     * 资质类型：1-合同首页 2-合同签字页 3-合同报价页
     */
    private Integer certificateType;

    /**
     * 证件状态(0-未审核。1-正常 2-审核未通过审核 4-不可用)
     */
    private Integer certificateStatus;

    /**
     * 描述信息
     */
    private String description;
}
