import React from 'react'
import { Link } from 'react-router-dom'
import { Button } from 'react-bootstrap'
import { FaHome } from 'react-icons/fa'

function NotFound() {
  return (
    <div className="text-center py-5">
      <h1 className="display-1 fw-bold">404</h1>
      <h2 className="mb-4">Página No Encontrada</h2>
      <p className="text-muted mb-4">
        Lo sentimos, la página que buscas no existe.
      </p>
      <Button as={Link} to="/" variant="primary">
        <FaHome className="me-2" />
        Volver al Inicio
      </Button>
    </div>
  )
}

export default NotFound
