package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoAddClassroom {
    @NotBlank(message = "error_entity_03")
    @Pattern(regexp = "^[A-Z]$", message = "error_entity_03")
    private String area;

    @NotNull(message = "error_entity_03")
    @Min(value = 0, message = "error_entity_03")
    @Max(value = 100, message = "error_entity_03")
    private Integer roomCode;

    @NotNull(message = "error_entity_03")
    @Min(value = 0, message = "error_entity_03")
    @Max(value = 1000, message = "error_entity_03")
    private Integer maxQuantity;
}
