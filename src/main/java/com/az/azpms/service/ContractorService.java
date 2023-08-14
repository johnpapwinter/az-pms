package com.az.azpms.service;

import com.az.azpms.domain.dto.ContractorDTO;
import org.springframework.data.domain.Page;

public interface ContractorService {

    Page<ContractorDTO> getAllContractors(int page, int size);

    void createContractor(ContractorDTO dto);

    void updateContractor(Long id, ContractorDTO dto);

    ContractorDTO getContractorById(Long id);

}
