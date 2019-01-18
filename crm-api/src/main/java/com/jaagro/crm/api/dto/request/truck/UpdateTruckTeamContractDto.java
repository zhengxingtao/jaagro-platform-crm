package com.jaagro.crm.api.dto.request.truck;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class UpdateTruckTeamContractDto implements Serializable {
    private static final long serialVersionUID = -1078819521276160892L;
    /**
     * 主键车队合同表ID
     */
    private Integer id;

    /**
     * 关联车队表ID
     */
    private Integer truckTeamId;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 业务类型(1 饲料运输 2 毛鸡运输 3 母猪 4 公猪 5 仔猪 6 生猪)
     */
    private Integer bussinessType;

    /**
     * 合同开始时间
     */
    private Date startDate;

    /**
     * 合同结束时间
     */
    private Date endDate;

    /**
     * 合同状态(0-待审核 1-审核通过)
     */
    private Integer contractStatus;

    /**
     * 备注信息
     */
    private String notes;

}
