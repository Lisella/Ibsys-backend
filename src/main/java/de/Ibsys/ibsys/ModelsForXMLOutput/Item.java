package de.Ibsys.ibsys.ModelsForXMLOutput;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class Item {
    @XmlAttribute(name = "article")
    private int article;

    @XmlAttribute(name = "quantity")
    private int quantity;

    // Constructors, getters, and setters
}