package com.enigma.livecode_loan_app.service;

import com.enigma.livecode_loan_app.entity.InstalmentType;

import java.util.List;

public interface InstalmentTypeService {
    InstalmentType createInstalmentType(InstalmentType instalmentType);
    InstalmentType getById(String id);
    List<InstalmentType> getAll();
    InstalmentType update(InstalmentType instalmentType);
    void delete(String id);
}
