package de.Ibsys.ibsys.ModelsForXMLOutput;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class SellDirectItem {
    @XmlAttribute(name = "article")
    private int article;

    @XmlAttribute(name = "quantity")
    private int quantity;

    @XmlAttribute(name = "price")
    private double price;

    @XmlAttribute(name = "penalty")
    private double penalty;

    // Constructors, getters, and setters
}