package com.az.azpms.service;


import com.az.azpms.domain.dto.ContractorDTO;
import com.az.azpms.domain.entities.Contractor;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.ContractorRepository;
import com.az.azpms.utils.Utils;
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
    @Transactional(readOnly = true)
    public Page<ContractorDTO> getAllContractors(Pageable pageable) {
        return contractorRepository.findAll(pageable)
                .map(this::toContractorDTO);
    }

    @Override
    @Transactional
    public void createContractor(ContractorDTO dto) {
        Contractor contractor = new Contractor();
        utils.initModelMapperStrict().map(dto, contractor);

        contractorRepository.save(contractor);
    }

    @Override
    @Transactional
    public void updateContractor(Long id, ContractorDTO dto) {
        Contractor contractor = contractorRepository.findById(id).orElseThrow(
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


    private ContractorDTO toContractorDTO(Contractor contractor) {
        ContractorDTO dto = new ContractorDTO();
        utils.initModelMapperStrict().map(contractor, dto);

        return dto;
    }
}
