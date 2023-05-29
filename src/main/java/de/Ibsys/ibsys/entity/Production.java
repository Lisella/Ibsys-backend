package de.Ibsys.ibsys.entity;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class Production {
    @XmlAttribute
    private int article;
    @XmlAttribute
    private int quantity;
}
