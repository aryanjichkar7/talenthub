package com.talenthub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which employee this attendance belongs to
    @ManyToOne                          // Many attendance records → One employee
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    // PRESENT, ABSENT, HALF_DAY
    @Enumerated(EnumType.STRING)        // Saves as text in DB, not number
    @Column(nullable = false)
    private AttendanceStatus status;

    @Column(name = "remarks")
    private String remarks;

    // Enum defined inside same file
    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        HALF_DAY
    }
}