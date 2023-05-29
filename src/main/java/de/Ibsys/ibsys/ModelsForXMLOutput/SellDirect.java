package de.Ibsys.ibsys.ModelsForXMLOutput;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "selldirect")
public class SellDirect {
    @XmlElement(name = "item")
    private List<SellDirectItem> items;

    // Constructors, getters, and setters
}