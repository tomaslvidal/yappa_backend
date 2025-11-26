import React, { useState, useEffect } from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
import { Card, Button, Row, Col, Badge } from 'react-bootstrap'
import { FaArrowLeft, FaEdit, FaTrash, FaUser, FaEnvelope, FaPhone, FaCalendar, FaIdCard, FaBuilding } from 'react-icons/fa'
import clienteService from '../services/clienteService'
import Loading from '../components/common/Loading'
import ErrorAlert from '../components/common/ErrorAlert'
import ConfirmDialog from '../components/common/ConfirmDialog'

function ClienteDetail() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [cliente, setCliente] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showConfirmDialog, setShowConfirmDialog] = useState(false)

  useEffect(() => {
    loadCliente()
  }, [id])

  const loadCliente = async () => {
    try {
      setLoading(true)
      setError(null)
      const data = await clienteService.getById(id)
      setCliente(data)
    } catch (err) {
      setError(err.response?.data?.message || 'Error al cargar el cliente')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async () => {
    try {
      await clienteService.delete(id)
      navigate('/clientes')
    } catch (err) {
      setError(err.response?.data?.message || 'Error al eliminar el cliente')
      setShowConfirmDialog(false)
    }
  }

  const formatDate = (dateString) => {
    if (!dateString) return '-'
    const date = new Date(dateString)
    return date.toLocaleDateString('es-AR', { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric' 
    })
  }

  const formatDateTime = (dateString) => {
    if (!dateString) return '-'
    const date = new Date(dateString)
    return date.toLocaleString('es-AR')
  }

  if (loading) {
    return <Loading message="Cargando detalles del cliente..." />
  }

  if (error) {
    return (
      <div>
        <ErrorAlert message={error} />
        <Button as={Link} to="/clientes" variant="primary">
          <FaArrowLeft className="me-2" />
          Volver a la Lista
        </Button>
      </div>
    )
  }

  if (!cliente) {
    return (
      <div className="text-center py-5">
        <h3>Cliente no encontrado</h3>
        <Button as={Link} to="/clientes" variant="primary" className="mt-3">
          <FaArrowLeft className="me-2" />
          Volver a la Lista
        </Button>
      </div>
    )
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Detalles del Cliente</h2>
        <div className="d-flex gap-2">
          <Button as={Link} to="/clientes" variant="secondary">
            <FaArrowLeft className="me-2" />
            Volver
          </Button>
          <Button as={Link} to={`/clientes/${id}/editar`} variant="warning">
            <FaEdit className="me-2" />
            Editar
          </Button>
          <Button onClick={() => setShowConfirmDialog(true)} variant="danger">
            <FaTrash className="me-2" />
            Eliminar
          </Button>
        </div>
      </div>

      <Card className="shadow-sm">
        <Card.Header className="bg-primary text-white">
          <h4 className="mb-0">
            <FaUser className="me-2" />
            {cliente.nombre} {cliente.apellido}
          </h4>
        </Card.Header>
        <Card.Body>
          <Row className="g-4">
            <Col md={6}>
              <h5 className="text-primary mb-3">Información Personal</h5>
              
              <div className="mb-3">
                <label className="text-muted small">Nombre</label>
                <div className="fw-bold">{cliente.nombre}</div>
              </div>

              <div className="mb-3">
                <label className="text-muted small">Apellido</label>
                <div className="fw-bold">{cliente.apellido}</div>
              </div>

              <div className="mb-3">
                <label className="text-muted small">
                  <FaCalendar className="me-2" />
                  Fecha de Nacimiento
                </label>
                <div className="fw-bold">{formatDate(cliente.fechaNacimiento)}</div>
              </div>
            </Col>

            <Col md={6}>
              <h5 className="text-primary mb-3">Información de Contacto</h5>
              
              <div className="mb-3">
                <label className="text-muted small">
                  <FaEnvelope className="me-2" />
                  Email
                </label>
                <div className="fw-bold">
                  <a href={`mailto:${cliente.email}`}>{cliente.email}</a>
                </div>
              </div>

              <div className="mb-3">
                <label className="text-muted small">
                  <FaPhone className="me-2" />
                  Teléfono Celular
                </label>
                <div className="fw-bold">{cliente.telefonoCelular}</div>
              </div>
            </Col>

            <Col md={6}>
              <h5 className="text-primary mb-3">Información Empresarial</h5>
              
              <div className="mb-3">
                <label className="text-muted small">
                  <FaBuilding className="me-2" />
                  Razón Social
                </label>
                <div className="fw-bold">{cliente.razonSocial}</div>
              </div>

              <div className="mb-3">
                <label className="text-muted small">
                  <FaIdCard className="me-2" />
                  CUIT
                </label>
                <div>
                  <Badge bg="secondary" className="fs-6">{cliente.cuit}</Badge>
                </div>
              </div>
            </Col>

            <Col md={6}>
              <h5 className="text-primary mb-3">Información del Sistema</h5>
              
              <div className="mb-3">
                <label className="text-muted small">ID del Cliente</label>
                <div className="fw-bold">{cliente.id}</div>
              </div>

              <div className="mb-3">
                <label className="text-muted small">Fecha de Creación</label>
                <div className="fw-bold">{formatDateTime(cliente.fechaCreacion)}</div>
              </div>

              <div className="mb-3">
                <label className="text-muted small">Última Modificación</label>
                <div className="fw-bold">{formatDateTime(cliente.fechaModificacion)}</div>
              </div>
            </Col>
          </Row>
        </Card.Body>
      </Card>

      <ConfirmDialog
        show={showConfirmDialog}
        title="Confirmar Eliminación"
        message={`¿Está seguro que desea eliminar al cliente ${cliente.nombre} ${cliente.apellido}? Esta acción no se puede deshacer.`}
        onConfirm={handleDelete}
        onCancel={() => setShowConfirmDialog(false)}
      />
    </div>
  )
}

export default ClienteDetail
