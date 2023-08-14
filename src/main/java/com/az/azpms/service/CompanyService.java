package com.az.azpms.service;

import com.az.azpms.domain.dto.CompanyDTO;
import org.springframework.data.domain.Page;


public interface CompanyService {

    Page<CompanyDTO> getAllCompanies(int page, int size);

    void createCompany(CompanyDTO dto);

    void updateCompany(Long id, CompanyDTO dto);

    CompanyDTO getCompanyById(Long id);
}
