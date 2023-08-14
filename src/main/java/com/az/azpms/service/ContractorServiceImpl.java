package com.az.azpms.service;


import com.az.azpms.domain.dto.ContractorDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ContractorServiceImpl implements ContractorService {

    @Override
    public Page<ContractorDTO> getAllContractors(int page, int size) {
        return null;
    }

    @Override
    public void createContractor(ContractorDTO dto) {

    }

    @Override
    public void updateContractor(Long id, ContractorDTO dto) {

    }

    @Override
    public ContractorDTO getContractorById(Long id) {
        return null;
    }
}
