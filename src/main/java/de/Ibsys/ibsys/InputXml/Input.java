package de.Ibsys.ibsys.InputXml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "input")
public class Input {
    private QualityControl qualityControl;
    private SellWish sellWish;
    private SellDirect sellDirect;
    private OrderList orderList;
    private ProductionList productionList;
    private WorkingTimeList workingTimeList;

    @XmlElement(name = "qualitycontrol")
    public QualityControl getQualityControl() {
        return qualityControl;
    }

    public void setQualityControl(QualityControl qualityControl) {
        this.qualityControl = qualityControl;
    }

    @XmlElement(name = "sellwish")
    public SellWish getSellWish() {
        return sellWish;
    }

    public void setSellWish(SellWish sellWish) {
        this.sellWish = sellWish;
    }

    @XmlElement(name = "selldirect")
    public SellDirect getSellDirect() {
        return sellDirect;
    }

    public void setSellDirect(SellDirect sellDirect) {
        this.sellDirect = sellDirect;
    }

    @XmlElement(name = "orderlist")
    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    @XmlElement(name = "productionlist")
    public ProductionList getProductionList() {
        return productionList;
    }

    public void setProductionList(ProductionList productionList) {
        this.productionList = productionList;
    }

    @XmlElement(name = "workingtimelist")
    public WorkingTimeList getWorkingTimeList() {
        return workingTimeList;
    }

    public void setWorkingTimeList(WorkingTimeList workingTimeList) {
        this.workingTimeList = workingTimeList;

    }}