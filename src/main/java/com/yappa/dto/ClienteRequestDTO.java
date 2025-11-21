package com.yappa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear o actualizar un cliente")
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Schema(description = "Nombre del cliente", example = "Juan", required = true)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    @Schema(description = "Apellido del cliente", example = "Pérez", required = true)
    private String apellido;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 150, message = "La razón social no puede exceder 150 caracteres")
    @Schema(description = "Razón social del cliente", example = "JP Servicios SRL", required = true)
    private String razonSocial;

    @NotBlank(message = "El CUIT es obligatorio")
    @Pattern(regexp = "^\\d{2}-\\d{8}-\\d{1}$", message = "El CUIT debe tener formato XX-XXXXXXXX-X")
    @Schema(description = "CUIT del cliente (formato: XX-XXXXXXXX-X)", example = "20-12345678-9", required = true)
    private String cuit;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Schema(description = "Fecha de nacimiento del cliente", example = "1985-06-15", required = true)
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El teléfono celular es obligatorio")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener 10 dígitos")
    @Schema(description = "Teléfono celular del cliente (10 dígitos)", example = "1165874210", required = true)
    private String telefonoCelular;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 150, message = "El email no puede exceder 150 caracteres")
    @Schema(description = "Email del cliente", example = "juan.perez@example.com", required = true)
    private String email;
}
