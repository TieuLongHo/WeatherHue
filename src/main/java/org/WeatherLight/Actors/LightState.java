package org.WeatherLight.Actors;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LightState {
    @JsonProperty("isOpen")
    private boolean isOpen;

    public LightState() {
    }
    public LightState(final boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    @Override
    public String toString() {
        return "LightState{" +
                "isOpen=" + isOpen +
                '}';
    }


}
