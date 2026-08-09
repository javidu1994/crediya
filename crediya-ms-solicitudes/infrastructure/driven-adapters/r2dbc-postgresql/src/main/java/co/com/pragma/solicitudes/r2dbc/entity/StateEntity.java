package co.com.pragma.solicitudes.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("states")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id_state")
    private Long idState;

    @Column("name")
    private String name;

    @Column("description")
    private String description;
}
