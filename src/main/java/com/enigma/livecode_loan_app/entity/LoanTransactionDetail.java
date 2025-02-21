package com.enigma.livecode_loan_app.entity;

import com.enigma.livecode_loan_app.constant.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trx_loan_detail")
@Builder
public class LoanTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "transaction_date", nullable = false)
    private Long transactionDate;

    @Column(name = "nominal", nullable = false)
    private Double nominal;

    @ManyToOne
    @JoinColumn(name = "trx_loan_id", nullable = false)
    private LoanTransaction loanTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", nullable = false)
    private LoanStatus loanStatus;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;
}
