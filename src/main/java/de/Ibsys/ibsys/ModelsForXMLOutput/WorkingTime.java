package de.Ibsys.ibsys.ModelsForXMLOutput;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "workingtime")
public class WorkingTime {
    @XmlAttribute(name = "station")
    private int station;

    @XmlAttribute(name = "shift")
    private int shift;

    @XmlAttribute(name = "overtime")
    private int overtime;

    // Constructors, getters, and setters
}