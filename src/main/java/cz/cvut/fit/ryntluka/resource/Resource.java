package cz.cvut.fit.ryntluka.resource;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;

import java.util.List;

public interface Resource<DTO, CreateDTO> {
    void create(CreateDTO data);
    void update(CreateDTO data, int id);
    DTO findById(int id);
    List<DTO> findByName(String name);
    List<DTO> findAll();
    void delete(int id);
}
