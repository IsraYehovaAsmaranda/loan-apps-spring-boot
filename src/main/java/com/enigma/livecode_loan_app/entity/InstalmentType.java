package com.enigma.livecode_loan_app.entity;

import com.enigma.livecode_loan_app.constant.EInstalmentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_installment_type")
@Builder
public class InstalmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "installment_type", nullable = false)
    private EInstalmentType instalmentType;
}
