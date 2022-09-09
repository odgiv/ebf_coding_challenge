import * as React from 'react'
import Button from '@mui/material/Button'
import TextField from '@mui/material/TextField'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogContent from '@mui/material/DialogContent'
import DialogTitle from '@mui/material/DialogTitle'
import {useFormik} from 'formik'
import * as Yup from 'yup'
import axios from 'axios'

const EmployeeFormSchema = Yup.object().shape({
  name: Yup.string().min(2, 'Too Short!').required('Required'),
  surname: Yup.string().min(2, 'Too Short!').required('Required'),
  email: Yup.string().email('Invalid email').required('Required'),
  address: Yup.string().required('Required'),
  salary: Yup.number().required('Required'),
})

export default function EmployeeFormDialog({
  open,
  handleClose,
  initialValues,
  id = null,
  companyId = null,
  callback = null,
}) {
  const resetForm = () => {
    formik.resetForm()
  }

  const formik = useFormik({
    initialValues,
    enableReinitialize: true,
    validationSchema: EmployeeFormSchema,
    onSubmit: async (values) => {
      let httpMethod = axios.post
      if (!!id) {
        httpMethod = axios.put
      }

      await httpMethod('http://localhost:8080/employee', {...values, companyId, id})
      if (!!callback) {
        callback()
      }
      resetForm()
      handleClose()
    },
  })

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>{!id ? 'New Empoyee' : 'Update employee data'}</DialogTitle>
      <form
        onSubmit={(values) => {
          formik.handleSubmit(values)
          // formik.resetForm()
        }}
      >
        <DialogContent sx={{padding: '20px 30px'}}>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Name"
            fullWidth
            variant="standard"
            value={formik.values.name}
            onChange={formik.handleChange}
            error={formik.errors.name && formik.touched.name}
            sx={{marginBottom: '10px'}}
            helperText={formik.errors.name && formik.touched.name && `${formik.errors.name}`}
          />
          <TextField
            autoFocus
            margin="dense"
            id="surname"
            label="Surname"
            fullWidth
            variant="standard"
            value={formik.values.surname}
            error={formik.errors.surname && formik.touched.surname}
            onChange={formik.handleChange}
            sx={{marginBottom: '10px'}}
            helperText={
              formik.errors.surname && formik.touched.surname && `${formik.errors.surname}`
            }
          />
          <TextField
            autoFocus
            margin="dense"
            id="email"
            label="Email Address"
            type="email"
            fullWidth
            variant="standard"
            value={formik.values.email}
            error={formik.errors.email && formik.touched.email}
            onChange={formik.handleChange}
            sx={{marginBottom: '10px'}}
            helperText={formik.errors.email && formik.touched.email && `${formik.errors.email}`}
          />
          <TextField
            autoFocus
            margin="dense"
            id="address"
            label="Address"
            fullWidth
            variant="standard"
            value={formik.values.address}
            error={formik.errors.address && formik.touched.address}
            onChange={formik.handleChange}
            sx={{marginBottom: '10px'}}
            helperText={
              formik.errors.address && formik.touched.address && `${formik.errors.address}`
            }
          />
          <TextField
            autoFocus
            margin="dense"
            id="salary"
            label="Salary"
            type="number"
            fullWidth
            variant="standard"
            value={formik.values.salary}
            error={formik.errors.salary && formik.touched.salary}
            onChange={formik.handleChange}
            sx={{marginBottom: '10px'}}
            helperText={formik.errors.salary && formik.touched.salary && `${formik.errors.salary}`}
          />
        </DialogContent>
        <DialogActions sx={{padding: '20px 30px'}}>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit">Submit</Button>
        </DialogActions>
      </form>
    </Dialog>
  )
}
