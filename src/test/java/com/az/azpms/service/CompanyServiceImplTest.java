package com.az.azpms.service;

import com.az.azpms.domain.dto.CompanyDTO;
import com.az.azpms.domain.entities.Company;
import com.az.azpms.domain.repository.CompanyRepository;
import com.az.azpms.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private Utils utils;

    @InjectMocks
    CompanyServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Get all companies")
    void shouldGetAllCompanies() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("Create a company")
    void shouldCreateCompany() {
        // given
        CompanyDTO dto = new CompanyDTO();

        when(companyRepository.findCompanyByTitle(dto.getTitle()))
                .thenReturn(Optional.empty());
        // when
        // then
    }

    @Test
    @DisplayName("Update information of a company")
    void shouldUpdateCompany() {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("Get a company by its ID")
    void shouldGetCompanyById() {
        // given
        // when
        // then
    }
}