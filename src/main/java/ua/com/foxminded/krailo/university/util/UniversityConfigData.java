package ua.com.foxminded.krailo.university.util;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.model.VocationKind;

@Transactional
@Component
@ConfigurationProperties
public class UniversityConfigData {

    private Map<VocationKind, Integer> vocationDurationBykind;
    private int groupMaxSize;

    public Map<VocationKind, Integer> getVocationDurationBykind() {
	return vocationDurationBykind;
    }

    public void setVocationDurationBykind(Map<VocationKind, Integer> vocationDurationBykind) {
	this.vocationDurationBykind = vocationDurationBykind;
    }

    public int getGroupMaxSize() {
	return groupMaxSize;
    }

    public void setGroupMaxSize(int groupMaxSize) {
	this.groupMaxSize = groupMaxSize;
    }

}
