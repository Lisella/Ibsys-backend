package de.Ibsys.ibsys.InputXml;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
public class Order {
    private int article;
    private int quantity;
    private int modus;

}
