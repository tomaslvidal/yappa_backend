import React, { useState, useEffect } from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
import { Card, Button, Form, Row, Col, Alert } from 'react-bootstrap'
import { FaArrowLeft, FaSave } from 'react-icons/fa'
import { Formik } from 'formik'
import * as Yup from 'yup'
import clienteService from '../services/clienteService'
import Loading from '../components/common/Loading'
import ErrorAlert from '../components/common/ErrorAlert'

function ClienteForm() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEditMode = !!id
  const [loading, setLoading] = useState(isEditMode)
  const [error, setError] = useState(null)
  const [initialValues, setInitialValues] = useState({
    nombre: '',
    apellido: '',
    razonSocial: '',
    cuit: '',
    fechaNacimiento: '',
    telefonoCelular: '',
    email: ''
  })

  useEffect(() => {
    if (isEditMode) {
      loadCliente()
    }
  }, [id])

  const loadCliente = async () => {
    try {
      setLoading(true)
      setError(null)
      const data = await clienteService.getById(id)
      setInitialValues({
        nombre: data.nombre || '',
        apellido: data.apellido || '',
        razonSocial: data.razonSocial || '',
        cuit: data.cuit || '',
        fechaNacimiento: data.fechaNacimiento || '',
        telefonoCelular: data.telefonoCelular || '',
        email: data.email || ''
      })
    } catch (err) {
      setError(err.response?.data?.message || 'Error al cargar el cliente')
    } finally {
      setLoading(false)
    }
  }

  const validationSchema = Yup.object({
    nombre: Yup.string()
      .required('El nombre es obligatorio')
      .max(100, 'El nombre no puede exceder 100 caracteres'),
    apellido: Yup.string()
      .required('El apellido es obligatorio')
      .max(100, 'El apellido no puede exceder 100 caracteres'),
    razonSocial: Yup.string()
      .required('La razón social es obligatoria')
      .max(150, 'La razón social no puede exceder 150 caracteres'),
    cuit: Yup.string()
      .required('El CUIT es obligatorio')
      .matches(/^\d{2}-\d{8}-\d{1}$/, 'El CUIT debe tener formato XX-XXXXXXXX-X'),
    fechaNacimiento: Yup.date()
      .required('La fecha de nacimiento es obligatoria')
      .max(new Date(), 'La fecha de nacimiento debe ser en el pasado'),
    telefonoCelular: Yup.string()
      .required('El teléfono celular es obligatorio')
      .matches(/^\d{10}$/, 'El teléfono debe tener 10 dígitos'),
    email: Yup.string()
      .required('El email es obligatorio')
      .email('El email debe ser válido')
      .max(150, 'El email no puede exceder 150 caracteres')
  })

  const handleSubmit = async (values, { setSubmitting, setFieldError }) => {
    try {
      setError(null)
      if (isEditMode) {
        await clienteService.update(id, values)
      } else {
        await clienteService.create(values)
      }
      navigate('/clientes')
    } catch (err) {
      const errorMessage = err.response?.data?.message || 
        (isEditMode ? 'Error al actualizar el cliente' : 'Error al crear el cliente')
      
      // Si el error es de validación del backend, intentar mostrar en el campo correspondiente
      if (err.response?.data?.errors) {
        Object.keys(err.response.data.errors).forEach(field => {
          setFieldError(field, err.response.data.errors[field])
        })
      } else {
        setError(errorMessage)
      }
    } finally {
      setSubmitting(false)
    }
  }

  if (loading) {
    return <Loading message={`Cargando ${isEditMode ? 'datos del cliente' : 'formulario'}...`} />
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>{isEditMode ? 'Editar Cliente' : 'Nuevo Cliente'}</h2>
        <Button as={Link} to="/clientes" variant="secondary">
          <FaArrowLeft className="me-2" />
          Volver
        </Button>
      </div>

      {error && <ErrorAlert message={error} onClose={() => setError(null)} />}

      <Card className="shadow-sm">
        <Card.Body>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleSubmit}
            enableReinitialize
          >
            {({
              values,
              errors,
              touched,
              handleChange,
              handleBlur,
              handleSubmit,
              isSubmitting
            }) => (
              <Form onSubmit={handleSubmit}>
                <Row>
                  <Col md={6}>
                    <h5 className="text-primary mb-3">Información Personal</h5>
                    
                    <Form.Group className="mb-3">
                      <Form.Label>Nombre *</Form.Label>
                      <Form.Control
                        type="text"
                        name="nombre"
                        value={values.nombre}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.nombre && errors.nombre}
                        placeholder="Ingrese el nombre"
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.nombre}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Apellido *</Form.Label>
                      <Form.Control
                        type="text"
                        name="apellido"
                        value={values.apellido}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.apellido && errors.apellido}
                        placeholder="Ingrese el apellido"
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.apellido}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Fecha de Nacimiento *</Form.Label>
                      <Form.Control
                        type="date"
                        name="fechaNacimiento"
                        value={values.fechaNacimiento}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.fechaNacimiento && errors.fechaNacimiento}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.fechaNacimiento}
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Col>

                  <Col md={6}>
                    <h5 className="text-primary mb-3">Información de Contacto</h5>
                    
                    <Form.Group className="mb-3">
                      <Form.Label>Email *</Form.Label>
                      <Form.Control
                        type="email"
                        name="email"
                        value={values.email}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.email && errors.email}
                        placeholder="ejemplo@correo.com"
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.email}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group className="mb-3">
                      <Form.Label>Teléfono Celular *</Form.Label>
                      <Form.Control
                        type="text"
                        name="telefonoCelular"
                        value={values.telefonoCelular}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.telefonoCelular && errors.telefonoCelular}
                        placeholder="1165874210 (10 dígitos)"
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.telefonoCelular}
                      </Form.Control.Feedback>
                      <Form.Text className="text-muted">
                        Ingrese 10 dígitos sin guiones ni espacios
                      </Form.Text>
                    </Form.Group>
                  </Col>
                </Row>

                <Row className="mt-3">
                  <Col md={12}>
                    <h5 className="text-primary mb-3">Información Empresarial</h5>
                    
                    <Row>
                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label>Razón Social *</Form.Label>
                          <Form.Control
                            type="text"
                            name="razonSocial"
                            value={values.razonSocial}
                            onChange={handleChange}
                            onBlur={handleBlur}
                            isInvalid={touched.razonSocial && errors.razonSocial}
                            placeholder="Ingrese la razón social"
                          />
                          <Form.Control.Feedback type="invalid">
                            {errors.razonSocial}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Col>

                      <Col md={6}>
                        <Form.Group className="mb-3">
                          <Form.Label>CUIT *</Form.Label>
                          <Form.Control
                            type="text"
                            name="cuit"
                            value={values.cuit}
                            onChange={handleChange}
                            onBlur={handleBlur}
                            isInvalid={touched.cuit && errors.cuit}
                            placeholder="20-12345678-9"
                          />
                          <Form.Control.Feedback type="invalid">
                            {errors.cuit}
                          </Form.Control.Feedback>
                          <Form.Text className="text-muted">
                            Formato: XX-XXXXXXXX-X
                          </Form.Text>
                        </Form.Group>
                      </Col>
                    </Row>
                  </Col>
                </Row>

                <Alert variant="info" className="mt-3">
                  <small>* Todos los campos son obligatorios</small>
                </Alert>

                <div className="d-flex gap-2 justify-content-end mt-4">
                  <Button 
                    as={Link} 
                    to="/clientes" 
                    variant="secondary"
                    disabled={isSubmitting}
                  >
                    Cancelar
                  </Button>
                  <Button 
                    type="submit" 
                    variant="primary"
                    disabled={isSubmitting}
                  >
                    <FaSave className="me-2" />
                    {isSubmitting ? 'Guardando...' : (isEditMode ? 'Actualizar' : 'Crear Cliente')}
                  </Button>
                </div>
              </Form>
            )}
          </Formik>
        </Card.Body>
      </Card>
    </div>
  )
}

export default ClienteForm
