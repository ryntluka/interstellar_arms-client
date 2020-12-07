package cz.cvut.fit.ryntluka.dto;

import java.awt.*;
import java.util.List;

public class PlanetCreateDTO {

    private final String name;
    private final Point coordinate;
    private final String territory;
    private final String nativeRace;
    private final List<Integer> inhabitantsIds;

    public PlanetCreateDTO(String name, Point coordinate, String territory, String nativeRace, List<Integer> inhabitantsIds) {
        this.name = name;
        this.coordinate = coordinate;
        this.territory = territory;
        this.nativeRace = nativeRace;
        this.inhabitantsIds = inhabitantsIds;
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

    public List<Integer> getInhabitantsIds() {
        return inhabitantsIds;
    }
}
