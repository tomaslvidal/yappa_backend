package com.yappa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta con información del cliente")
public class ClienteResponseDTO {

    @Schema(description = "ID único del cliente", example = "1")
    private Long id;

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Pérez")
    private String apellido;

    @Schema(description = "Razón social del cliente", example = "JP Servicios SRL")
    private String razonSocial;

    @Schema(description = "CUIT del cliente", example = "20-12345678-9")
    private String cuit;

    @Schema(description = "Fecha de nacimiento del cliente", example = "1985-06-15")
    private LocalDate fechaNacimiento;

    @Schema(description = "Teléfono celular del cliente", example = "1165874210")
    private String telefonoCelular;

    @Schema(description = "Email del cliente", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Fecha de creación del registro", example = "2024-11-21T10:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última modificación del registro", example = "2024-11-21T10:30:00")
    private LocalDateTime fechaModificacion;
}
