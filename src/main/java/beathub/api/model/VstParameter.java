package beathub.api.model;

public class VstParameter {
    private int parameterNumber;
    private String name;
    private boolean toggle;
    private double value;
    private double min;
    private double max;
    private String unit;

    public int getParameterNumber() {
        return parameterNumber;
    }

    public void setParameterNumber(int parameterNumber) {
        this.parameterNumber = parameterNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
