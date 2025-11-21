package com.yappa.service;

import com.yappa.dto.ClienteRequestDTO;
import com.yappa.dto.ClienteResponseDTO;
import java.util.List;

public interface ClienteService {
    
    ClienteResponseDTO crearCliente(ClienteRequestDTO requestDTO);
    
    ClienteResponseDTO obtenerClientePorId(Long id);
    
    List<ClienteResponseDTO> obtenerTodosLosClientes();
    
    ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO requestDTO);
    
    void eliminarCliente(Long id);
    
    ClienteResponseDTO buscarPorCuit(String cuit);
    
    ClienteResponseDTO buscarPorEmail(String email);
}
