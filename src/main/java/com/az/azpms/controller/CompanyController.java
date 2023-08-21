package com.az.azpms.controller;

import com.az.azpms.domain.dto.CompanyDTO;
import com.az.azpms.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<Page<CompanyDTO>> getAllCompanies(Pageable pageable) {
        Page<CompanyDTO> response = companyService.getAllCompanies(pageable);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createCompany(@RequestBody @Valid CompanyDTO dto) {
        companyService.createCompany(dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCompany(@RequestBody @Valid CompanyDTO dto) {
        companyService.updateCompany(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("id") Long id) {
        CompanyDTO response = companyService.getCompanyById(id);

        return ResponseEntity.ok().body(response);
    }

}
