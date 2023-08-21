package com.az.azpms.controller;

import com.az.azpms.domain.dto.ContractorDTO;
import com.az.azpms.service.ContractorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contractors")
public class ContractorController {

    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping
    public ResponseEntity<Page<ContractorDTO>> getAllContractors(Pageable pageable) {
        Page<ContractorDTO> response = contractorService.getAllContractors(pageable);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createContractor(@RequestBody @Valid ContractorDTO dto) {
        contractorService.createContractor(dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateContractor(@RequestBody @Valid ContractorDTO dto) {
        contractorService.updateContractor(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ContractorDTO> getContractorById(@PathVariable("id") Long id) {
        ContractorDTO response = contractorService.getContractorById(id);

        return ResponseEntity.ok().body(response);
    }

}
