package de.Ibsys.ibsys.entity;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;
@Data
@XmlRootElement
public class WorkingTimeList {
    private List<WorkingTime> workingtime;
}
