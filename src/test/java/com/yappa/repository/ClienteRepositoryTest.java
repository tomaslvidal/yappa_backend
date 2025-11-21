package com.yappa.repository;

import com.yappa.entity.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@DisplayName("Tests del Repositorio de Clientes")
class ClienteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Guardar y buscar cliente por ID")
    void testGuardarYBuscarCliente() {
        // Given
        Cliente cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .build();

        // When
        Cliente savedCliente = entityManager.persistAndFlush(cliente);
        Optional<Cliente> found = clienteRepository.findById(savedCliente.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("Juan", found.get().getNombre());
        assertEquals("20-12345678-9", found.get().getCuit());
    }

    @Test
    @DisplayName("Verificar si existe cliente por CUIT")
    void testExistsByCuit() {
        // Given
        Cliente cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .build();

        entityManager.persistAndFlush(cliente);

        // When
        boolean exists = clienteRepository.existsByCuit("20-12345678-9");

        // Then
        assertTrue(exists);
    }

    @Test
    @DisplayName("Verificar si existe cliente por email")
    void testExistsByEmail() {
        // Given
        Cliente cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .build();

        entityManager.persistAndFlush(cliente);

        // When
        boolean exists = clienteRepository.existsByEmail("juan.perez@example.com");

        // Then
        assertTrue(exists);
    }

    @Test
    @DisplayName("Buscar cliente por CUIT")
    void testFindByCuit() {
        // Given
        Cliente cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .build();

        entityManager.persistAndFlush(cliente);

        // When
        Optional<Cliente> found = clienteRepository.findByCuit("20-12345678-9");

        // Then
        assertTrue(found.isPresent());
        assertEquals("Juan", found.get().getNombre());
    }

    @Test
    @DisplayName("Buscar cliente por email")
    void testFindByEmail() {
        // Given
        Cliente cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .build();

        entityManager.persistAndFlush(cliente);

        // When
        Optional<Cliente> found = clienteRepository.findByEmail("juan.perez@example.com");

        // Then
        assertTrue(found.isPresent());
        assertEquals("20-12345678-9", found.get().getCuit());
    }

    @Test
    @DisplayName("CUIT único - No permite duplicados")
    void testCuitUnique() {
        // Given
        Cliente cliente1 = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .razonSocial("JP Servicios SRL")
                .cuit("20-12345678-9")
                .fechaNacimiento(LocalDate.of(1985, 6, 15))
                .telefonoCelular("1165874210")
                .email("juan.perez@example.com")
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombre("Carlos")
                .apellido("López")
                .razonSocial("CL Construcciones")
                .cuit("20-12345678-9") // Mismo CUIT
                .fechaNacimiento(LocalDate.of(1978, 1, 10))
                .telefonoCelular("1165874332")
                .email("carlos.lopez@example.com")
                .build();

        // When & Then
        entityManager.persistAndFlush(cliente1);
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(cliente2);
        });
    }
}
