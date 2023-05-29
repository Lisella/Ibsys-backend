package de.Ibsys.ibsys.ModelsForXMLOutput;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orderlist")
public class OrderList {
    @XmlElement(name = "order")
    private List<Order> orders;

    // Constructors, getters, and setters
}