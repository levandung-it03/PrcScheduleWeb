package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDtoUpdateComputerRoom {
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
