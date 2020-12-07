package cz.cvut.fit.ryntluka.dto;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PlanetDTO {

    private final int id;
    private final String name;
    private final Point coordinate;
    private final String territory;
    private final String nativeRace;
    private final List<Integer> inhabitantsIds;

    public PlanetDTO(int id, String name, Point coordinate, String territory, String nativeRace, List<Integer> inhabitantsIds) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.territory = territory;
        this.nativeRace = nativeRace;
        this.inhabitantsIds = inhabitantsIds;
    }

    public int getId() {
        return id;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinate, territory, nativeRace, inhabitantsIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PlanetDTO planet = (PlanetDTO) obj;
        return id == planet.id
                && Objects.equals(name, planet.name)
                && Objects.equals(coordinate, planet.coordinate)
                && Objects.equals(territory, planet.territory)
                && Objects.equals(nativeRace, planet.nativeRace)
                && Objects.equals(inhabitantsIds, planet.inhabitantsIds);
    }
}
