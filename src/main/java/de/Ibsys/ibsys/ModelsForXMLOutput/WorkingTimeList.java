package de.Ibsys.ibsys.ModelsForXMLOutput;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "workingtimelist")
public class WorkingTimeList {
    @XmlElement(name = "workingtime")
    private List<WorkingTime> workingTimes;

    // Constructors, getters, and setters
}