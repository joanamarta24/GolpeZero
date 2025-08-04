public class LightState {
    private final boolean on;
    private final int hue;

    public SmartLightApi(boolean on, int hue) {
        this.on = on;
        this.hue = hue;
    }

    public LightState(boolean on, int hue) {
        this.on = on;
        this.hue = hue;
    }

    public boolean isOn() {
        return on;
    }

    public int getHue() {
        return hue;
    }

}
