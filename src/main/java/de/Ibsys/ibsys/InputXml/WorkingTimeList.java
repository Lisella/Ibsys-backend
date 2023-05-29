package de.Ibsys.ibsys.InputXml;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;
@Data
@XmlRootElement
public class WorkingTimeList {
    private List<WorkingTime> workingtime;
}
