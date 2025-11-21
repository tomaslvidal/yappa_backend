package com.yappa.service.impl;

import com.yappa.dto.ClienteRequestDTO;
import com.yappa.dto.ClienteResponseDTO;
import com.yappa.entity.Cliente;
import com.yappa.exception.BusinessException;
import com.yappa.exception.ResourceNotFoundException;
import com.yappa.repository.ClienteRepository;
import com.yappa.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteResponseDTO crearCliente(ClienteRequestDTO requestDTO) {
        log.info("Creando nuevo cliente con CUIT: {}", requestDTO.getCuit());
        
        // Validar CUIT único
        if (clienteRepository.existsByCuit(requestDTO.getCuit())) {
            throw new BusinessException("Ya existe un cliente con el CUIT: " + requestDTO.getCuit());
        }
        
        // Validar email único
        if (clienteRepository.existsByEmail(requestDTO.getEmail())) {
            throw new BusinessException("Ya existe un cliente con el email: " + requestDTO.getEmail());
        }
        
        Cliente cliente = convertirDTOaEntidad(requestDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        log.info("Cliente creado exitosamente con ID: {}", clienteGuardado.getId());
        return convertirEntidadADTO(clienteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerClientePorId(Long id) {
        log.info("Buscando cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        return convertirEntidadADTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerTodosLosClientes() {
        log.info("Obteniendo todos los clientes");
        return clienteRepository.findAll().stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO requestDTO) {
        log.info("Actualizando cliente con ID: {}", id);
        
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        
        // Validar CUIT único (si cambió)
        if (!clienteExistente.getCuit().equals(requestDTO.getCuit()) && 
            clienteRepository.existsByCuit(requestDTO.getCuit())) {
            throw new BusinessException("Ya existe un cliente con el CUIT: " + requestDTO.getCuit());
        }
        
        // Validar email único (si cambió)
        if (!clienteExistente.getEmail().equals(requestDTO.getEmail()) && 
            clienteRepository.existsByEmail(requestDTO.getEmail())) {
            throw new BusinessException("Ya existe un cliente con el email: " + requestDTO.getEmail());
        }
        
        actualizarEntidadDesdeDTO(clienteExistente, requestDTO);
        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        
        log.info("Cliente actualizado exitosamente con ID: {}", id);
        return convertirEntidadADTO(clienteActualizado);
    }

    @Override
    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        }
        
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado exitosamente con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorCuit(String cuit) {
        log.info("Buscando cliente con CUIT: {}", cuit);
        Cliente cliente = clienteRepository.findByCuit(cuit)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con CUIT: " + cuit));
        return convertirEntidadADTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorEmail(String email) {
        log.info("Buscando cliente con email: {}", email);
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con email: " + email));
        return convertirEntidadADTO(cliente);
    }

    // Métodos auxiliares de conversión
    private Cliente convertirDTOaEntidad(ClienteRequestDTO dto) {
        return Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .razonSocial(dto.getRazonSocial())
                .cuit(dto.getCuit())
                .fechaNacimiento(dto.getFechaNacimiento())
                .telefonoCelular(dto.getTelefonoCelular())
                .email(dto.getEmail())
                .build();
    }

    private ClienteResponseDTO convertirEntidadADTO(Cliente cliente) {
        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .razonSocial(cliente.getRazonSocial())
                .cuit(cliente.getCuit())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .telefonoCelular(cliente.getTelefonoCelular())
                .email(cliente.getEmail())
                .fechaCreacion(cliente.getFechaCreacion())
                .fechaModificacion(cliente.getFechaModificacion())
                .build();
    }

    private void actualizarEntidadDesdeDTO(Cliente cliente, ClienteRequestDTO dto) {
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setRazonSocial(dto.getRazonSocial());
        cliente.setCuit(dto.getCuit());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setTelefonoCelular(dto.getTelefonoCelular());
        cliente.setEmail(dto.getEmail());
    }
}
