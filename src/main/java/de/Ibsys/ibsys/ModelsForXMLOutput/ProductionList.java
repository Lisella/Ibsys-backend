package de.Ibsys.ibsys.ModelsForXMLOutput;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "productionlist")
public class ProductionList {
    @XmlElement(name = "production")
    public List<Production> productions;
}