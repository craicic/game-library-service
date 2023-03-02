package org.motoc.gamelibrary.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The product line of a game
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_line", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_name"))
public class ProductLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_line_seq_gen")
    @SequenceGenerator(name = "product_line_seq_gen", sequenceName = "product_line_sequence", initialValue = 1)
    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @Column(name = "lower_case_name", nullable = false)
    private String lowerCaseName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "productLine")
    private Set<Game> games = new HashSet<>();

    // Overridden accessors
    public void setName(String name) {
        this.name = name;
        this.lowerCaseName = name.toLowerCase();
    }

    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseName = name.toLowerCase();
    }

    public void addGame(Game game) {
        this.games.add(game);
        game.setProductLine(this);
    }

    public void removeGame(Game game) {
        this.games.remove(game);
        game.setProductLine(null);
    }
}