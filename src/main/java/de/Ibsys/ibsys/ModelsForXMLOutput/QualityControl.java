package de.Ibsys.ibsys.ModelsForXMLOutput;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "qualitycontrol")
public class QualityControl {
    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "losequantity")
    private int losequantity;

    @XmlAttribute(name = "delay")
    private int delay;

    // Constructors, getters, and setters
}