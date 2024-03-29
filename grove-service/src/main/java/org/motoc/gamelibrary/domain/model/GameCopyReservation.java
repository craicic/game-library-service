package org.motoc.gamelibrary.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_copy_reservation", schema = "public")
public class GameCopyReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_copy_reservation_seq_gen")
    @SequenceGenerator(name = "game_copy_reservation_seq_gen", sequenceName = "game_copy_reservation_sequence", initialValue = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_game_copy")
    private GameCopy gameCopy;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_reservation")
    private Reservation reservation;

    private boolean isLate;
}
