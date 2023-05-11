package de.Ibsys.ibsys.entity;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement
public class OrderList {
    private List<Order> order;

}
