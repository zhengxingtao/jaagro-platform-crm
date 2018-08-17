package com.jaagro.crm.api.dto.request.customer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 修改客户联系人帮助类
 *
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class UpdateCustomerContractDto implements Serializable {
    /**
     * 客户审核记录主键id
     */
    private Long id;

    /**
     * 外键关联客户ID(References customer)
     */
    private Long customerId;

    /**
     * 联系人员
     */
    private String contractName;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态(0 停用 1 启用)
     */
    private Integer status;
}
