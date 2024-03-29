package org.motoc.gamelibrary.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.motoc.gamelibrary.domain.enumeration.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservation", schema = "public")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq_gen")
    @SequenceGenerator(name = "reservation_seq_gen", sequenceName = "reservation_sequence", initialValue = 1)
    private Long id;

    private LocalDateTime dateTimeOfWithdrawal;
    private LocalDateTime dateTimeOfReturn;

    @Column(nullable = false)
    private LocalDate dateOfStart;
    private LocalDate dateOfEnding;

    @Column(nullable = false)
    private LoanStatus status;

    @PastOrPresent
    private LocalDateTime dateTimeOfCreation;

    /**
     * In euro
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "Total amount value cannot be below 0.0")
    @Digits(integer = 10, fraction = 2, message = "Total amount maximum integer part is 10, maximum fractional part is 2")
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "reservation")
    private Set<GameCopyReservation> gameCopyReservations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_administrator")
    private Administrator administrator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_adherent")
    private Adherent adherent;
}
