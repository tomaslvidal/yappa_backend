import React from 'react'
import { Spinner } from 'react-bootstrap'

function Loading({ message = 'Cargando...' }) {
  return (
    <div className="spinner-container">
      <div className="text-center">
        <Spinner animation="border" role="status" variant="primary">
          <span className="visually-hidden">Cargando...</span>
        </Spinner>
        <p className="mt-3 text-muted">{message}</p>
      </div>
    </div>
  )
}

export default Loading
