package com.enigma.livecode_loan_app.service.impl;

import com.enigma.livecode_loan_app.entity.InstalmentType;
import com.enigma.livecode_loan_app.repository.InstalmentTypeRepository;
import com.enigma.livecode_loan_app.service.InstalmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstalmentTypeServiceImpl implements InstalmentTypeService {
    private final InstalmentTypeRepository instalmentTypeRepository;

    @Override
    public InstalmentType createInstalmentType(InstalmentType instalmentType) {
        return instalmentTypeRepository.saveAndFlush(instalmentType);
    }

    @Override
    public InstalmentType getById(String id) {
        return findByIdOrThrow(id);
    }

    @Override
    public List<InstalmentType> getAll() {
        return instalmentTypeRepository.findAll();
    }

    @Override
    public InstalmentType update(InstalmentType instalmentType) {
        findByIdOrThrow(instalmentType.getId());
        return instalmentTypeRepository.saveAndFlush(instalmentType);
    }

    private InstalmentType findByIdOrThrow(String id) {
        return instalmentTypeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "INSTALLMENT TYPE NOT FOUND"));
    }

    @Override
    public void delete(String id) {
        findByIdOrThrow(id);
        instalmentTypeRepository.deleteById(id);
    }
}
