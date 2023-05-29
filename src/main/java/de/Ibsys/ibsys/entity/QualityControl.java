package de.Ibsys.ibsys.entity;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class QualityControl {
    @XmlAttribute
    private String type;
    @XmlAttribute
    private int losequantity;
    @XmlAttribute
    private int delay;
}

