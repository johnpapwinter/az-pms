package com.az.azpms.service;

import com.az.azpms.domain.dto.CompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Override
    public Page<CompanyDTO> getAllCompanies(int page, int size) {
        return null;
    }

    @Override
    public void createCompany(CompanyDTO dto) {

    }

    @Override
    public void updateCompany(Long id, CompanyDTO dto) {

    }

    @Override
    public CompanyDTO getCompanyById(Long id) {
        return null;
    }
}
