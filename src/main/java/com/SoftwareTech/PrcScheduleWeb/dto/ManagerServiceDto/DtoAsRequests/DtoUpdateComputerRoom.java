package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsRequests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUpdateComputerRoom {
    @NotNull(message = "error_entity_03")
    @Min(value = 0, message = "error_entity_03")
    @Max(value = 1000, message = "error_entity_03")
    private Integer maxQuantity;

    @NotNull(message = "error_entity_03")
    @Min(value = 0, message = "error_entity_03")
    @Max(value = 1000, message = "error_entity_03")
    private Integer maxComputerQuantity;

    @NotNull(message = "error_entity_03")
    @Min(value = 0, message = "error_entity_03")
    @Max(value = 1000, message = "error_entity_03")
    private Integer availableComputerQuantity;

    @NotNull(message = "error_entity_03")
    private boolean status;

}
