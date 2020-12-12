package cz.cvut.fit.ryntluka.dto;

import org.springframework.hateoas.RepresentationModel;

import java.awt.*;
import java.util.Objects;

public class PlanetDTO extends RepresentationModel<PlanetDTO> implements ModelDTO {

    private final int id;
    private final String name;
    private final Point coordinate;
    private final String territory;
    private final String nativeRace;

    public PlanetDTO(int id, String name, Point coordinate, String territory, String nativeRace) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.territory = territory;
        this.nativeRace = nativeRace;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinate, territory, nativeRace);
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
                && Objects.equals(nativeRace, planet.nativeRace);
    }
}
