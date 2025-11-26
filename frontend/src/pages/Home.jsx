import React from 'react'
import { Link } from 'react-router-dom'
import { Card, Button, Row, Col } from 'react-bootstrap'
import { FaUsers, FaPlus, FaList } from 'react-icons/fa'

function Home() {
  return (
    <div>
      <div className="text-center mb-5">
        <h1 className="display-4 fw-bold">Bienvenido a Yappa</h1>
        <p className="lead text-muted">
          Sistema de Gestión de Clientes
        </p>
      </div>

      <Row className="g-4">
        <Col md={6}>
          <Card className="h-100">
            <Card.Body className="text-center">
              <FaUsers size={48} className="text-primary mb-3" />
              <Card.Title>Ver Clientes</Card.Title>
              <Card.Text>
                Visualiza y administra todos los clientes registrados en el sistema.
              </Card.Text>
              <Button 
                as={Link} 
                to="/clientes" 
                variant="primary"
                className="d-flex align-items-center justify-content-center mx-auto"
                style={{ width: 'fit-content' }}
              >
                <FaList className="me-2" />
                Ver Lista de Clientes
              </Button>
            </Card.Body>
          </Card>
        </Col>

        <Col md={6}>
          <Card className="h-100">
            <Card.Body className="text-center">
              <FaPlus size={48} className="text-success mb-3" />
              <Card.Title>Nuevo Cliente</Card.Title>
              <Card.Text>
                Registra un nuevo cliente en el sistema con toda su información.
              </Card.Text>
              <Button 
                as={Link} 
                to="/clientes/nuevo" 
                variant="success"
                className="d-flex align-items-center justify-content-center mx-auto"
                style={{ width: 'fit-content' }}
              >
                <FaPlus className="me-2" />
                Crear Nuevo Cliente
              </Button>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Card className="mt-4">
        <Card.Body>
          <Card.Title>Características del Sistema</Card.Title>
          <Row>
            <Col md={4}>
              <h6 className="text-primary">✓ ABM Completo</h6>
              <p className="text-muted small">
                Alta, baja y modificación de clientes
              </p>
            </Col>
            <Col md={4}>
              <h6 className="text-primary">✓ Validación de Datos</h6>
              <p className="text-muted small">
                Validación de CUIT, email y teléfono
              </p>
            </Col>
            <Col md={4}>
              <h6 className="text-primary">✓ Búsqueda Avanzada</h6>
              <p className="text-muted small">
                Búsqueda por CUIT, email y más
              </p>
            </Col>
          </Row>
        </Card.Body>
      </Card>
    </div>
  )
}

export default Home
