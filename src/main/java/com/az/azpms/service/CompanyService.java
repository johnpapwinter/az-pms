package com.az.azpms.service;

import com.az.azpms.domain.dto.CompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CompanyService {

    Page<CompanyDTO> getAllCompanies(Pageable pageable);

    void createCompany(CompanyDTO dto);

    void updateCompany(CompanyDTO dto);

    CompanyDTO getCompanyById(Long id);
}
