import React from 'react'
import { Container } from 'react-bootstrap'

function Footer() {
  const currentYear = new Date().getFullYear()

  return (
    <footer className="bg-dark text-white mt-auto py-3">
      <Container>
        <div className="text-center">
          <p className="mb-0">
            &copy; {currentYear} Yappa - Sistema de Gestión de Clientes
          </p>
          <small className="text-muted">
            Desarrollado con React + Spring Boot
          </small>
        </div>
      </Container>
    </footer>
  )
}

export default Footer
