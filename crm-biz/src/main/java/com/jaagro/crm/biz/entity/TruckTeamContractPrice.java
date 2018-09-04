package com.jaagro.crm.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TruckTeamContractPrice {
    /**
     * 
     */
    private Integer id;

    /**
     * 合同id
     */
    private Integer truckTeamContractId;

    /**
     * 计价方式
     */
    private Integer pricingType;

    /**
     * 车辆类型
     */
    private Integer vehicleType;

    /**
     * 货物类型
     */
    private Integer productType;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 是否有效
     */
    private Boolean priceStatus;

    /**
     * 报价生效时间
     */
    private Date startDate;

    /**
     * 报价截止时间
     */
    private Date endDate;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 合同id
     * @return truck_team_contract_id 合同id
     */
    public Integer getTruckTeamContractId() {
        return truckTeamContractId;
    }

    /**
     * 合同id
     * @param truckTeamContractId 合同id
     */
    public void setTruckTeamContractId(Integer truckTeamContractId) {
        this.truckTeamContractId = truckTeamContractId;
    }

    /**
     * 计价方式
     * @return pricing_type 计价方式
     */
    public Integer getPricingType() {
        return pricingType;
    }

    /**
     * 计价方式
     * @param pricingType 计价方式
     */
    public void setPricingType(Integer pricingType) {
        this.pricingType = pricingType;
    }

    /**
     * 车辆类型
     * @return vehicle_type 车辆类型
     */
    public Integer getVehicleType() {
        return vehicleType;
    }

    /**
     * 车辆类型
     * @param vehicleType 车辆类型
     */
    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * 货物类型
     * @return product_type 货物类型
     */
    public Integer getProductType() {
        return productType;
    }

    /**
     * 货物类型
     * @param productType 货物类型
     */
    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    /**
     * 单价
     * @return price 单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 单价
     * @param price 单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 是否有效
     * @return price_status 是否有效
     */
    public Boolean getPriceStatus() {
        return priceStatus;
    }

    /**
     * 是否有效
     * @param priceStatus 是否有效
     */
    public void setPriceStatus(Boolean priceStatus) {
        this.priceStatus = priceStatus;
    }

    /**
     * 报价生效时间
     * @return start_date 报价生效时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 报价生效时间
     * @param startDate 报价生效时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 报价截止时间
     * @return end_date 报价截止时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 报价截止时间
     * @param endDate 报价截止时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}