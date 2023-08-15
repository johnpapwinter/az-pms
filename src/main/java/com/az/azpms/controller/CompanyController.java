package com.az.azpms.controller;

import com.az.azpms.domain.dto.CompanyDTO;
import com.az.azpms.service.CompanyService;
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
    public ResponseEntity<Void> createCompany(@RequestBody CompanyDTO dto) {
        companyService.createCompany(dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable("id") Long id, @RequestBody CompanyDTO dto) {
        companyService.updateCompany(id, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("id") Long id) {
        CompanyDTO response = companyService.getCompanyById(id);

        return ResponseEntity.ok().body(response);
    }

}
