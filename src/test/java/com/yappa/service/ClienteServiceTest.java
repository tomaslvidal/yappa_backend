package com.yappa.service;

import com.yappa.dto.ClienteRequestDTO;
import com.yappa.dto.ClienteResponseDTO;
import com.yappa.entity.Cliente;
import com.yappa.exception.BusinessException;
import com.yappa.exception.ResourceNotFoundException;
import com.yappa.repository.ClienteRepository;
import com.yappa.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Servicio de Clientes")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteRequestDTO requestDTO;
    private Cliente cliente;

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

        cliente = Cliente.builder()
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
    @DisplayName("Crear cliente exitosamente")
    void testCrearCliente_Success() {
        // Given
        when(clienteRepository.existsByCuit(anyString())).thenReturn(false);
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        ClienteResponseDTO response = clienteService.crearCliente(requestDTO);

        // Then
        assertNotNull(response);
        assertEquals("Juan", response.getNombre());
        assertEquals("20-12345678-9", response.getCuit());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Crear cliente con CUIT duplicado debe lanzar excepción")
    void testCrearCliente_CuitDuplicado() {
        // Given
        when(clienteRepository.existsByCuit(anyString())).thenReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> clienteService.crearCliente(requestDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Crear cliente con email duplicado debe lanzar excepción")
    void testCrearCliente_EmailDuplicado() {
        // Given
        when(clienteRepository.existsByCuit(anyString())).thenReturn(false);
        when(clienteRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThrows(BusinessException.class, () -> clienteService.crearCliente(requestDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Obtener cliente por ID exitosamente")
    void testObtenerClientePorId_Success() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        ClienteResponseDTO response = clienteService.obtenerClientePorId(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan", response.getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Obtener cliente por ID inexistente debe lanzar excepción")
    void testObtenerClientePorId_NotFound() {
        // Given
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> clienteService.obtenerClientePorId(999L));
    }

    @Test
    @DisplayName("Obtener todos los clientes")
    void testObtenerTodosLosClientes() {
        // Given
        Cliente cliente2 = Cliente.builder()
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

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente, cliente2));

        // When
        List<ClienteResponseDTO> clientes = clienteService.obtenerTodosLosClientes();

        // Then
        assertNotNull(clientes);
        assertEquals(2, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Actualizar cliente exitosamente")
    void testActualizarCliente_Success() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        ClienteResponseDTO response = clienteService.actualizarCliente(1L, requestDTO);

        // Then
        assertNotNull(response);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Eliminar cliente exitosamente")
    void testEliminarCliente_Success() {
        // Given
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        // When
        clienteService.eliminarCliente(1L);

        // Then
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar cliente inexistente debe lanzar excepción")
    void testEliminarCliente_NotFound() {
        // Given
        when(clienteRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> clienteService.eliminarCliente(999L));
        verify(clienteRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Buscar cliente por CUIT exitosamente")
    void testBuscarPorCuit_Success() {
        // Given
        when(clienteRepository.findByCuit("20-12345678-9")).thenReturn(Optional.of(cliente));

        // When
        ClienteResponseDTO response = clienteService.buscarPorCuit("20-12345678-9");

        // Then
        assertNotNull(response);
        assertEquals("20-12345678-9", response.getCuit());
    }

    @Test
    @DisplayName("Buscar cliente por email exitosamente")
    void testBuscarPorEmail_Success() {
        // Given
        when(clienteRepository.findByEmail("juan.perez@example.com")).thenReturn(Optional.of(cliente));

        // When
        ClienteResponseDTO response = clienteService.buscarPorEmail("juan.perez@example.com");

        // Then
        assertNotNull(response);
        assertEquals("juan.perez@example.com", response.getEmail());
    }
}
