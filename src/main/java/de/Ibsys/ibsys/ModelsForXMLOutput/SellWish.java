package de.Ibsys.ibsys.ModelsForXMLOutput;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellwish")
public class SellWish {
    @XmlElement(name = "item")
    private List<Item> items;

    // Constructors, getters, and setters
}