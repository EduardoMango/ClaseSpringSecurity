package org.eduardomango.clasespringsecurity.controllers;

import org.eduardomango.clasespringsecurity.model.dto.CuentaDTO;
import org.eduardomango.clasespringsecurity.services.CuentaService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping()
    public ResponseEntity<List<CuentaDTO>> findAllByUserId(){
        return ResponseEntity.ok(cuentaService.findAll());
    }

}
