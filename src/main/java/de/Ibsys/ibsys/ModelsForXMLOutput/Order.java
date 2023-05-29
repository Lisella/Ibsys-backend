package de.Ibsys.ibsys.ModelsForXMLOutput;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
public class Order {
    @XmlAttribute(name = "article")
    private int article;

    @XmlAttribute(name = "quantity")
    private int quantity;

    @XmlAttribute(name = "modus")
    private int modus;

    // Constructors, getters, and setters
}