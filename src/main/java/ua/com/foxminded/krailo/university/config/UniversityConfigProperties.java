package ua.com.foxminded.krailo.university.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ua.com.foxminded.krailo.university.model.VocationKind;

@Configuration
@ConfigurationProperties
public class UniversityConfigProperties {

    private Map<VocationKind, Integer> vocationDurationBykind;
    private int maxGroupSize;

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

}
