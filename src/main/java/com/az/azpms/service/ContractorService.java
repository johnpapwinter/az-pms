package com.az.azpms.service;

import com.az.azpms.domain.dto.ContractorDTO;
import com.az.azpms.domain.dto.SearchContractorParamsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractorService {

    void createContractor(ContractorDTO dto);

    void updateContractor(ContractorDTO dto);

    ContractorDTO getContractorById(Long id);

    Page<ContractorDTO> searchByParameters(SearchContractorParamsDTO dto, Pageable pageable);

}
