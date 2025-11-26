import api from './api';

const CLIENTES_ENDPOINT = '/clientes';

export const clienteService = {
  // Obtener todos los clientes
  getAll: async () => {
    const response = await api.get(CLIENTES_ENDPOINT);
    return response.data;
  },

  // Obtener un cliente por ID
  getById: async (id) => {
    const response = await api.get(`${CLIENTES_ENDPOINT}/${id}`);
    return response.data;
  },

  // Crear un nuevo cliente
  create: async (clienteData) => {
    const response = await api.post(CLIENTES_ENDPOINT, clienteData);
    return response.data;
  },

  // Actualizar un cliente existente
  update: async (id, clienteData) => {
    const response = await api.put(`${CLIENTES_ENDPOINT}/${id}`, clienteData);
    return response.data;
  },

  // Eliminar un cliente
  delete: async (id) => {
    const response = await api.delete(`${CLIENTES_ENDPOINT}/${id}`);
    return response.data;
  },

  // Buscar cliente por CUIT
  getByCuit: async (cuit) => {
    const response = await api.get(`${CLIENTES_ENDPOINT}/cuit/${cuit}`);
    return response.data;
  },

  // Buscar cliente por email
  getByEmail: async (email) => {
    const response = await api.get(`${CLIENTES_ENDPOINT}/email/${email}`);
    return response.data;
  },
};

export default clienteService;
