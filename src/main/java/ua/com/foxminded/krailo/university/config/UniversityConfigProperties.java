package ua.com.foxminded.krailo.university.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import ua.com.foxminded.krailo.university.model.VocationKind;

@ConfigurationProperties(prefix = "university")
public class UniversityConfigProperties {

    private Map<VocationKind, Integer> vocationDurationBykind;
    private int maxGroupSize;
    private int ageConstraint;

    public Map<VocationKind, Integer> getVocationDurationBykind() {
	return vocationDurationBykind;
    }

    public void setVocationDurationBykind(Map<VocationKind, Integer> vocationDurationBykind) {
	this.vocationDurationBykind = vocationDurationBykind;
    }

    public int getMaxGroupSize() {
	return maxGroupSize;
    }

    public void setMaxGroupSize(int maxGroupSize) {
	this.maxGroupSize = maxGroupSize;
    }

    public int getAgeConstraint() {
        return ageConstraint;
    }

    public void setAgeConstraint(int ageConstraint) {
        this.ageConstraint = ageConstraint;
    }
    


}
