import React from 'react'
import { Link, useLocation } from 'react-router-dom'
import { Navbar as BsNavbar, Nav, Container } from 'react-bootstrap'
import { FaUsers, FaHome } from 'react-icons/fa'

function Navbar() {
  const location = useLocation()

  return (
    <BsNavbar bg="primary" variant="dark" expand="lg" className="shadow-sm">
      <Container>
        <BsNavbar.Brand as={Link} to="/">
          <strong>Yappa</strong>
        </BsNavbar.Brand>
        <BsNavbar.Toggle aria-controls="basic-navbar-nav" />
        <BsNavbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link 
              as={Link} 
              to="/" 
              active={location.pathname === '/'}
              className="d-flex align-items-center"
            >
              <FaHome className="me-2" />
              Inicio
            </Nav.Link>
            <Nav.Link 
              as={Link} 
              to="/clientes" 
              active={location.pathname.startsWith('/clientes')}
              className="d-flex align-items-center"
            >
              <FaUsers className="me-2" />
              Clientes
            </Nav.Link>
          </Nav>
        </BsNavbar.Collapse>
      </Container>
    </BsNavbar>
  )
}

export default Navbar
