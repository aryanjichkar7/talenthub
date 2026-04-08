package com.talenthub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "payroll")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "month_year")        // e.g., "APRIL-2026"
    private String monthYear;

    @Column(name = "basic_salary")
    private Double basicSalary;

    @Column(name = "hra")
    private Double hra;

    @Column(name = "allowances")
    private Double allowances;

    @Column(name = "gross_salary")
    private Double grossSalary;

    @Column(name = "pf_deduction")
    private Double pfDeduction;

    @Column(name = "esi_deduction")
    private Double esiDeduction;

    @Column(name = "net_salary")
    private Double netSalary;

    @Column(name = "generated_date")
    private LocalDate generatedDate;
}
