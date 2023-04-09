package com.dh.backend1.martin.finalclinicaodontologica.controller;

import com.dh.backend1.martin.finalclinicaodontologica.modeldto.PacienteDto;
import com.dh.backend1.martin.finalclinicaodontologica.modeldto.TurnoDto;
import com.dh.backend1.martin.finalclinicaodontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    @Autowired
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<TurnoDto> crearTurno(@RequestBody TurnoDto turnoDto) {

        if( turnoDto.getOdontologo() == null || turnoDto.getPaciente() == null)
            return ResponseEntity.badRequest().build();
        if( turnoDto.getFecha() == null){
            turnoDto.setFecha(LocalDateTime.now());
        }
        if (turnoDto.getPaciente().getFechaAlta() == null){
            turnoDto.getPaciente().setFechaAlta(LocalDate.now());
        }
        return ResponseEntity.ok(turnoService.crearTurno(turnoDto));

    }

    @PutMapping("/actualizar")
    public ResponseEntity<TurnoDto> actualizarTurno(@RequestBody TurnoDto turnoDto) {

        if( turnoDto.getId() == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(turnoService.update(turnoDto));

    }
    @GetMapping("/listar")
    public ResponseEntity<Iterable<TurnoDto>> listarTurno() {
        return ResponseEntity.ok(turnoService.findAll());
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDto> buscarTurno(@PathVariable Integer id) {
        if (id == null || id < 0)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(turnoService.findById(id));
    }
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<TurnoDto> borrarTurno(@PathVariable Integer id) {

        if (id == null || id < 0)
            return ResponseEntity.badRequest().build();
        if (turnoService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        turnoService.delete(id);
        return ResponseEntity.ok().build();
    }


}