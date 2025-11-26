import React from 'react'
import { Alert } from 'react-bootstrap'
import { FaExclamationTriangle } from 'react-icons/fa'

function ErrorAlert({ message, onClose }) {
  return (
    <Alert variant="danger" dismissible={!!onClose} onClose={onClose}>
      <div className="d-flex align-items-center">
        <FaExclamationTriangle className="me-2" />
        <div>
          <strong>Error: </strong>
          {message}
        </div>
      </div>
    </Alert>
  )
}

export default ErrorAlert
