package co.com.pragma.solicitudes.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StateDTO {

    private Long id;
    private String name;
    private String description;
}
