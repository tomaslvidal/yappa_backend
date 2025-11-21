package com.yappa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yappa.dto.ClienteRequestDTO;
import com.yappa.dto.ClienteResponseDTO;
import com.yappa.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@DisplayName("Tests del Controlador de Clientes")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private ClienteRequestDTO requestDTO;
    private ClienteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = ClienteRequestDTO.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .build();

        responseDTO = ClienteResponseDTO.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .fechaCreacion(LocalDateTime.now())
                .fechaModificacion(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("POST /api/clientes - Crear cliente exitosamente")
    void testCrearCliente_Success() throws Exception {
        // Given
        when(clienteService.crearCliente(any(ClienteRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.cuit").value("20-12345678-9"));
    }

    @Test
    @DisplayName("POST /api/clientes - Validación falla con datos inválidos")
    void testCrearCliente_ValidationFails() throws Exception {
        // Given
        ClienteRequestDTO invalidDTO = ClienteRequestDTO.builder()
                .nombre("") // Nombre vacío
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("invalid-cuit") // CUIT inválido
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("123") // Teléfono inválido
                .email("invalid-email") // Email inválido
                .build();

        // When & Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/clientes/{id} - Obtener cliente por ID")
    void testObtenerClientePorId_Success() throws Exception {
        // Given
        when(clienteService.obtenerClientePorId(1L)).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    @DisplayName("GET /api/clientes - Obtener todos los clientes")
    void testObtenerTodosLosClientes() throws Exception {
        // Given
        ClienteResponseDTO responseDTO2 = ClienteResponseDTO.builder()
                .id(2L)
                .nombre("María")
                .apellido("Gómez")
                .razonSocial("MG Soluciones")
                .cuit("27-23456789-0")
                .fechaNacimiento(LocalDate.of(1990, 9, 21))
                .telefonoCelular("1165874221")
                .email("maria.gomez@example.com")
                .fechaCreacion(LocalDateTime.now())
                .fechaModificacion(LocalDateTime.now())
                .build();

        List<ClienteResponseDTO> clientes = Arrays.asList(responseDTO, responseDTO2);
        when(clienteService.obtenerTodosLosClientes()).thenReturn(clientes);

        // When & Then
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].nombre").value("María"));
    }

    @Test
    @DisplayName("PUT /api/clientes/{id} - Actualizar cliente")
    void testActualizarCliente_Success() throws Exception {
        // Given
        when(clienteService.actualizarCliente(eq(1L), any(ClienteRequestDTO.class))).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    @DisplayName("DELETE /api/clientes/{id} - Eliminar cliente")
    void testEliminarCliente_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/clientes/cuit/{cuit} - Buscar por CUIT")
    void testBuscarPorCuit_Success() throws Exception {
        // Given
        when(clienteService.buscarPorCuit("20-12345678-9")).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/api/clientes/cuit/20-12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuit").value("20-12345678-9"));
    }

    @Test
    @DisplayName("GET /api/clientes/email/{email} - Buscar por email")
    void testBuscarPorEmail_Success() throws Exception {
        // Given
        when(clienteService.buscarPorEmail("juan.perez@example.com")).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(get("/api/clientes/email/juan.perez@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"));
    }
}
