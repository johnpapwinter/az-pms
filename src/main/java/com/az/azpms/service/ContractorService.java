package com.az.azpms.service;

import com.az.azpms.domain.dto.ContractorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractorService {

    Page<ContractorDTO> getAllContractors(Pageable pageable);

    void createContractor(ContractorDTO dto);

    void updateContractor(ContractorDTO dto);

    ContractorDTO getContractorById(Long id);

}
