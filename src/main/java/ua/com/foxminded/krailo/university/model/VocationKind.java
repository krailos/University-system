package ua.com.foxminded.krailo.university.model;

public enum VocationKind {

    GENERAL("Gneral", 14),
    PREFERENTIAL("Preferential", 24);

    private String name;
    private int maxDuration;

    private VocationKind(String name, int duration) {
	this.name = name;
	this.maxDuration = duration;
    }

    public String getName() {
	return name;
    }

    public int getMaxDuration() {
	return maxDuration;
    }

}
