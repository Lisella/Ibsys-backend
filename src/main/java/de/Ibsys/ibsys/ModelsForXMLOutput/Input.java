package de.Ibsys.ibsys.ModelsForXMLOutput;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "input")
public class Input {
    @XmlElement(name = "qualitycontrol")
    private QualityControl qualityControl;

    @XmlElement(name = "sellwish")
    private SellWish sellWish;

    @XmlElement(name = "selldirect")
    private SellDirect sellDirect;

    @XmlElement(name = "orderlist")
    private OrderList orderList;

    @XmlElement(name = "productionlist")
    private ProductionList productionList;

    @XmlElement(name = "workingtimelist")
    private WorkingTimeList workingTimeList;
}
