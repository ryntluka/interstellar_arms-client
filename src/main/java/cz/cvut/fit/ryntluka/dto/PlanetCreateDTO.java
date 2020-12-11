package cz.cvut.fit.ryntluka.dto;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PlanetCreateDTO {

    private final String name;
    private final Point coordinate;
    private final String territory;
    private final String nativeRace;

    public PlanetCreateDTO(String name, Point coordinate, String territory, String nativeRace) {
        this.name = name;
        this.coordinate = coordinate;
        this.territory = territory;
        this.nativeRace = nativeRace;
    }

    public String getName() {
        return name;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public String getTerritory() {
        return territory;
    }

    public String getNativeRace() {
        return nativeRace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinate, territory, nativeRace);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PlanetCreateDTO planet = (PlanetCreateDTO) obj;
        return Objects.equals(name, planet.name)
                && Objects.equals(coordinate, planet.coordinate)
                && Objects.equals(territory, planet.territory)
                && Objects.equals(nativeRace, planet.nativeRace);
    }
}
