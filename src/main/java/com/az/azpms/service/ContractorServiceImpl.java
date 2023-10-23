package com.az.azpms.service;


import com.az.azpms.domain.dto.ContractorDTO;
import com.az.azpms.domain.dto.SearchContractorParamsDTO;
import com.az.azpms.domain.entities.Contractor;
import com.az.azpms.domain.entities.QContractor;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.ContractorRepository;
import com.az.azpms.utils.Utils;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final Utils utils;

    public ContractorServiceImpl(ContractorRepository contractorRepository,
                                 Utils utils) {
        this.contractorRepository = contractorRepository;
        this.utils = utils;
    }

    @Override
    @Transactional
    public void createContractor(ContractorDTO dto) {
        contractorRepository.findContractorByName(dto.getName()).ifPresent(
                contractor -> {
                    throw new AzAlreadyExistsException(AzErrorMessages.ENTITY_ALREADY_EXISTS.name());
                }
        );

        Contractor contractor = new Contractor();
        utils.initModelMapperStrict().map(dto, contractor);

        contractorRepository.save(contractor);
    }

    @Override
    @Transactional
    public void updateContractor(ContractorDTO dto) {
        Contractor contractor = contractorRepository.findById(dto.getId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        utils.initModelMapperStrict().map(dto, contractor);

        contractorRepository.save(contractor);
    }

    @Override
    @Transactional(readOnly = true)
    public ContractorDTO getContractorById(Long id) {
        Contractor contractor = contractorRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return toContractorDTO(contractor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractorDTO> searchByParameters(SearchContractorParamsDTO dto, Pageable pageable) {
        QContractor qContractor = QContractor.contractor;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dto.getName() != null) {
            booleanBuilder.and(qContractor.name.containsIgnoreCase(dto.getName()));
        }

        if (dto.getEmail() != null) {
            booleanBuilder.and(qContractor.email.containsIgnoreCase(dto.getEmail()));
        }

        if (dto.getAddress() != null) {
            booleanBuilder.and(qContractor.address.containsIgnoreCase(dto.getAddress()));
        }

        if (dto.getPhone1() != null) {
            booleanBuilder.and(qContractor.phone1.containsIgnoreCase(dto.getPhone1()));
        }

        if (dto.getPhone2() != null) {
            booleanBuilder.and(qContractor.phone2.containsIgnoreCase(dto.getPhone2()));
        }

        if (dto.getCity() != null) {
            booleanBuilder.and(qContractor.city.containsIgnoreCase(dto.getCity()));
        }

        if (dto.getCountry() != null) {
            booleanBuilder.and(qContractor.country.containsIgnoreCase(dto.getCountry()));
        }

        return contractorRepository.findAll(booleanBuilder, pageable)
                .map(this::toContractorDTO);
    }

    private ContractorDTO toContractorDTO(Contractor contractor) {
        ContractorDTO dto = new ContractorDTO();
        utils.initModelMapperStrict().map(contractor, dto);

        return dto;
    }
}
