package co.com.pragma.solicitudes.model.state;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class State {

    private Long idState;
    private String name;
    private String description;
}
