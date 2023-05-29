package de.Ibsys.ibsys.ModelsForXMLOutput;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellwish")
public class SellWish {
    @XmlElement(name = "item")
    private List<Item> items;

    // Constructors, getters, and setters
}