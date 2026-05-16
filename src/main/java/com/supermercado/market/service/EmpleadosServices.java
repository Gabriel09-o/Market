package com.supermercado.market.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermercado.market.dto.EmpleadoDTO;
import com.supermercado.market.dto.EmpleadosMapper;
import com.supermercado.market.entity.Cargo;
import com.supermercado.market.entity.Empleado;
import com.supermercado.market.repository.EmpleadoRepository;

/* Servicio para la gestión de empleados. */
@Service
public class EmpleadosServices {

    @Autowired
    private EmpleadoRepository empleadosRepository;

    @Autowired
    private EmpleadosMapper empleadosMapper;

    public List<EmpleadoDTO> listarPorCargo(Cargo cargo) {

        return empleadosRepository.findByCargos(cargo)
                .stream()
                .map(empleado -> empleadosMapper.toDTO(empleado))
                .collect(Collectors.toList());
    }

    /* Lista empleados por rango de fechas */
    public List<EmpleadoDTO> listarPorRangoFecha(LocalDate inicio, LocalDate fin) {
        return empleadosRepository.findByFechaIngresoBetween(inicio, fin)
                .stream()
                .map(empleado -> empleadosMapper.toDTO(empleado))
                .collect(Collectors.toList());
    }

    /* Guarda un nuevo empleado */
    public EmpleadoDTO saveEmpleado(EmpleadoDTO empleadoDTO) {

        Empleado empleado = empleadosMapper.getEmpleado(empleadoDTO);
        return empleadosMapper.toDTO(empleadosRepository.save(empleado));
    }
}
