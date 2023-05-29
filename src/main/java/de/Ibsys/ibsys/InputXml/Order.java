package de.Ibsys.ibsys.InputXml;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class Order {
    @XmlAttribute
    private int article;
    @XmlAttribute
    private int quantity;
    @XmlAttribute
    private int modus;

}
