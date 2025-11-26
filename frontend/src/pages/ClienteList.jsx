import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { Table, Button, Card, Badge, Form, InputGroup } from 'react-bootstrap'
import { FaPlus, FaEye, FaEdit, FaTrash, FaSearch } from 'react-icons/fa'
import clienteService from '../services/clienteService'
import Loading from '../components/common/Loading'
import ErrorAlert from '../components/common/ErrorAlert'
import ConfirmDialog from '../components/common/ConfirmDialog'

function ClienteList() {
  const [clientes, setClientes] = useState([])
  const [filteredClientes, setFilteredClientes] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [searchTerm, setSearchTerm] = useState('')
  const [showConfirmDialog, setShowConfirmDialog] = useState(false)
  const [selectedClienteId, setSelectedClienteId] = useState(null)

  useEffect(() => {
    loadClientes()
  }, [])

  useEffect(() => {
    filterClientes()
  }, [searchTerm, clientes])

  const loadClientes = async () => {
    try {
      setLoading(true)
      setError(null)
      const data = await clienteService.getAll()
      setClientes(data)
      setFilteredClientes(data)
    } catch (err) {
      setError(err.response?.data?.message || 'Error al cargar los clientes')
    } finally {
      setLoading(false)
    }
  }

  const filterClientes = () => {
    if (!searchTerm.trim()) {
      setFilteredClientes(clientes)
      return
    }

    const term = searchTerm.toLowerCase()
    const filtered = clientes.filter(cliente => 
      cliente.nombre.toLowerCase().includes(term) ||
      cliente.apellido.toLowerCase().includes(term) ||
      cliente.razonSocial.toLowerCase().includes(term) ||
      cliente.cuit.includes(term) ||
      cliente.email.toLowerCase().includes(term)
    )
    setFilteredClientes(filtered)
  }

  const handleDeleteClick = (id) => {
    setSelectedClienteId(id)
    setShowConfirmDialog(true)
  }

  const handleDeleteConfirm = async () => {
    try {
      await clienteService.delete(selectedClienteId)
      setShowConfirmDialog(false)
      setSelectedClienteId(null)
      loadClientes()
    } catch (err) {
      setError(err.response?.data?.message || 'Error al eliminar el cliente')
      setShowConfirmDialog(false)
    }
  }

  const formatDate = (dateString) => {
    if (!dateString) return '-'
    const date = new Date(dateString)
    return date.toLocaleDateString('es-AR')
  }

  if (loading) {
    return <Loading message="Cargando clientes..." />
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Gestión de Clientes</h2>
        <Button as={Link} to="/clientes/nuevo" variant="success">
          <FaPlus className="me-2" />
          Nuevo Cliente
        </Button>
      </div>

      {error && <ErrorAlert message={error} onClose={() => setError(null)} />}

      <Card className="mb-4">
        <Card.Body>
          <InputGroup>
            <InputGroup.Text>
              <FaSearch />
            </InputGroup.Text>
            <Form.Control
              type="text"
              placeholder="Buscar por nombre, apellido, razón social, CUIT o email..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </InputGroup>
        </Card.Body>
      </Card>

      <Card>
        <Card.Body>
          {filteredClientes.length === 0 ? (
            <div className="text-center py-5">
              <p className="text-muted">
                {searchTerm ? 'No se encontraron clientes con ese criterio de búsqueda' : 'No hay clientes registrados'}
              </p>
              {!searchTerm && (
                <Button as={Link} to="/clientes/nuevo" variant="primary">
                  <FaPlus className="me-2" />
                  Crear el Primer Cliente
                </Button>
              )}
            </div>
          ) : (
            <div className="table-responsive">
              <Table hover>
                <thead className="table-light">
                  <tr>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Razón Social</th>
                    <th>CUIT</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th className="text-center">Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredClientes.map((cliente) => (
                    <tr key={cliente.id}>
                      <td>{cliente.nombre}</td>
                      <td>{cliente.apellido}</td>
                      <td>{cliente.razonSocial}</td>
                      <td>
                        <Badge bg="secondary">{cliente.cuit}</Badge>
                      </td>
                      <td>{cliente.email}</td>
                      <td>{cliente.telefonoCelular}</td>
                      <td>
                        <div className="d-flex gap-2 justify-content-center">
                          <Button
                            as={Link}
                            to={`/clientes/${cliente.id}`}
                            variant="info"
                            size="sm"
                            title="Ver detalles"
                          >
                            <FaEye />
                          </Button>
                          <Button
                            as={Link}
                            to={`/clientes/${cliente.id}/editar`}
                            variant="warning"
                            size="sm"
                            title="Editar"
                          >
                            <FaEdit />
                          </Button>
                          <Button
                            onClick={() => handleDeleteClick(cliente.id)}
                            variant="danger"
                            size="sm"
                            title="Eliminar"
                          >
                            <FaTrash />
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>
          )}
          
          {filteredClientes.length > 0 && (
            <div className="mt-3 text-muted">
              Mostrando {filteredClientes.length} de {clientes.length} cliente(s)
            </div>
          )}
        </Card.Body>
      </Card>

      <ConfirmDialog
        show={showConfirmDialog}
        title="Confirmar Eliminación"
        message="¿Está seguro que desea eliminar este cliente? Esta acción no se puede deshacer."
        onConfirm={handleDeleteConfirm}
        onCancel={() => setShowConfirmDialog(false)}
      />
    </div>
  )
}

export default ClienteList
