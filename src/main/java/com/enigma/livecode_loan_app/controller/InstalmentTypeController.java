package com.enigma.livecode_loan_app.controller;

import com.enigma.livecode_loan_app.entity.InstalmentType;
import com.enigma.livecode_loan_app.model.response.CommonResponse;
import com.enigma.livecode_loan_app.service.InstalmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/instalment-types")
@RequiredArgsConstructor
public class InstalmentTypeController {
    private final InstalmentTypeService instalmentTypeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<InstalmentType>> createInstalmentType(@RequestBody InstalmentType instalmentType) {
        InstalmentType response = instalmentTypeService.createInstalmentType(instalmentType);

        CommonResponse<InstalmentType> commonResponse = CommonResponse.<InstalmentType>builder()
                .message("SUCCESSFULLY CREATED INSTALMENT TYPE")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<InstalmentType>> getInstalmentTypeById(@PathVariable String id) {
        InstalmentType response = instalmentTypeService.getById(id);

        CommonResponse<InstalmentType> commonResponse = CommonResponse.<InstalmentType>builder()
                .message("SUCCESSFULLY FETCH INSTALMENT TYPE BY ID")
                .data(response)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<InstalmentType>>> getAllInstalmentTypes() {
        List<InstalmentType> instalmentTypes = instalmentTypeService.getAll();

        CommonResponse<List<InstalmentType>> commonResponse = CommonResponse.<List<InstalmentType>>builder()
                .message("SUCCESSFULLY FETCH INSTALMENT")
                .data(instalmentTypes)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<InstalmentType>> updateInstalmentType(@RequestBody InstalmentType instalmentType) {
        InstalmentType response = instalmentTypeService.update(instalmentType);

        CommonResponse<InstalmentType> commonResponse = CommonResponse.<InstalmentType>builder()
                .message("SUCCESSFULLY UPDATED INSTALMENT TYPE")
                .data(response)
                .build();

        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<String>> deleteInstalmentType(@PathVariable String id) {
        instalmentTypeService.delete(id);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .message("SUCCESSFULLY DELETED INSTALMENT TYPE")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}
