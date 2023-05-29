package de.Ibsys.ibsys.entity;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class WorkingTime {
    @XmlAttribute
    private int station;
    @XmlAttribute
    private int shift;
    @XmlAttribute
    private int overtime;

}
