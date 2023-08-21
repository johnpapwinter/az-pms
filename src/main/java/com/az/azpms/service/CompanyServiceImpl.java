package com.az.azpms.service;

import com.az.azpms.domain.dto.CompanyDTO;
import com.az.azpms.domain.entities.Company;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.CompanyRepository;
import com.az.azpms.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final Utils utils;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              Utils utils) {
        this.companyRepository = companyRepository;
        this.utils = utils;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(this::toCompanyDTO);
    }

    @Override
    @Transactional
    public void createCompany(CompanyDTO dto) {
        companyRepository.findCompanyByTitle(dto.getTitle()).ifPresent(
                company -> {
                    throw new AzAlreadyExistsException(AzErrorMessages.ENTITY_ALREADY_EXISTS.name());
                }
        );

        Company company = new Company();
        utils.initModelMapperStrict().map(dto, company);

        companyRepository.save(company);
    }

    @Override
    @Transactional
    public void updateCompany(CompanyDTO dto) {
        Company company = companyRepository.findById(dto.getId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        company.setTitle(dto.getTitle());
        company.setDescription(dto.getDescription());
        company.setAddress(dto.getAddress());
        company.setCity(dto.getCity());
        company.setCountry(dto.getCountry());

        companyRepository.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return toCompanyDTO(company);
    }


    private CompanyDTO toCompanyDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        utils.initModelMapperStrict().map(company, dto);

        return dto;
    }

}
