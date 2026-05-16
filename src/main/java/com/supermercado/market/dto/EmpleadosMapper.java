package com.supermercado.market.dto;

import org.springframework.stereotype.Component;

import com.supermercado.market.entity.Cargo;
import com.supermercado.market.entity.Empleado;

/**
 * Mapper para convertir entre Empleado y EmpleadoDTO.
 */
@Component
public class EmpleadosMapper {

    /**
     * Convierte una entidad Empleado a un DTO EmpleadoDTO.
     * @param empleado La entidad Empleado a convertir.
     * @return El DTO EmpleadoDTO resultante.
     */
    public EmpleadoDTO toDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();

        dto.setNombre(empleado.getNombre());
        dto.setCedula(empleado.getCedula());
        dto.setCargo(empleado.getCargo().name());
        dto.setFechaIngreso(empleado.getFechaIngreso());
        dto.setSalario(empleado.getSalario());

        return dto;
    }

    /**
     * Convierte un DTO EmpleadoDTO a una entidad Empleado.
     * @param dto El DTO EmpleadoDTO a convertir.
     * @return La entidad Empleado resultante.
     */
    public Empleado getEmpleado(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();

        empleado.setNombre(dto.getNombre());
        empleado.setCedula(dto.getCedula());
        empleado.setCargo(Cargo.valueOf(dto.getCargo()));
        empleado.setFechaIngreso(dto.getFechaIngreso());
        empleado.setSalario(dto.getSalario());

        return empleado;
    }

}
