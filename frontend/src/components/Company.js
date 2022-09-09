import * as React from 'react'
import '../App.css'
import {DataGrid} from '@mui/x-data-grid'
import {Button, IconButton} from '@mui/material'
import EmployeeFormDialog from './EmployeeFormDialog'
import ConfirmationDialog from './ConfirmationDialog'
import {AiOutlineUserAdd, AiOutlineDelete, AiOutlineEdit, AiOutlineEye} from 'react-icons/ai'
import {NavLink, useParams} from 'react-router-dom'
import axios from 'axios'
import Layout from './Layout'

function Company() {
  const [companyData, setCompanyData] = React.useState(null)
  const [employeesData, setEmployeesData] = React.useState([])
  const [employeeDialogOpen, setDialogOpen] = React.useState(false)
  const [confirmDialogOpen, setConfirmDialogOpen] = React.useState(false)
  const [refreshIndex, setRefreshIndex] = React.useState(0)
  const [rowCountState, setRowCountState] = React.useState(0)
  const [pageSize, setPageSize] = React.useState(10)
  const [page, setPage] = React.useState(0)
  const [currentEmployee, setCurrentEmployee] = React.useState({
    name: '',
    surname: '',
    email: '',
    address: '',
    salary: '',
  })

  const {companyId} = useParams()

  const columns = [
    {field: 'id', headerName: 'ID', width: 50},
    {field: 'name', headerName: 'Name', width: 200},
    {field: 'surname', headerName: 'Surname', width: 200},
    {field: 'email', headerName: 'Email', width: 200},
    {field: 'address', headerName: 'Address', width: 150},
    {field: 'salary', headerName: 'Salary', width: 100},
    {
      field: 'edit',
      headerName: 'Edit',
      sortable: false,
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation() // don't select this row after clicking

          const api = params.api
          const thisRow = {}

          api
            .getAllColumns()
            .filter((c) => !['__check__', 'edit', 'delete', 'view'].includes(c.field) && !!c)
            .forEach((c) => (thisRow[c.field] = params.getValue(params.id, c.field)))

          // return alert(JSON.stringify(thisRow, null, 4))
          setCurrentEmployee(thisRow)
          // console.log(thisRow)
          setDialogOpen(true)
        }

        return (
          <IconButton onClick={onClick} size="small">
            <AiOutlineEdit />
          </IconButton>
        )
      },
    },
    {
      field: 'delete',
      headerName: 'Delete',
      sortable: false,
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation() // don't select this row after clicking

          const api = params.api
          const thisRow = {}

          api
            .getAllColumns()
            .filter((c) => !['__check__', 'edit', 'delete', 'view'].includes(c.field) && !!c)
            .forEach((c) => (thisRow[c.field] = params.getValue(params.id, c.field)))

          setCurrentEmployee(thisRow)
          setConfirmDialogOpen(true)
        }

        return (
          <IconButton onClick={onClick} size="small">
            <AiOutlineDelete />
          </IconButton>
        )
      },
    },
    {
      field: 'view',
      headerName: 'Detail',
      sortable: false,
      renderCell: (params) => {
        return (
          <NavLink to={'/employee/' + params.row.id} style={{color: 'inherit'}}>
            <AiOutlineEye />
          </NavLink>
        )
      },
    },
  ]

  const refresh = () => {
    setRefreshIndex(refreshIndex + 1)
  }

  React.useEffect(() => {
    async function fetchCompanyData() {
      const {data} = await axios.get('http://localhost:8080/company/' + companyId)
      setCompanyData(data)
    }

    async function fetchEmployeeData() {
      const {data} = await axios.get('http://localhost:8080/company/' + companyId + '/employees', {
        params: {pageSize, page},
      })

      const {employees, totalEmployees} = data

      setEmployeesData(employees)
      setRowCountState((prevRowCountState) => {
        return totalEmployees !== undefined ? totalEmployees : prevRowCountState
      })
    }

    fetchCompanyData()
    fetchEmployeeData()
  }, [refreshIndex, companyId, pageSize, page])

  return (
    <Layout headerTitle="Company info">
      <div className="DataGridHeader">
        <div>
          <span className="font-medium">Company name:</span> {companyData ? companyData.name : ''}
          <br />
          <span className="font-medium">Average salary:</span>{' '}
          {companyData ? companyData.averageSalary : ''}
        </div>
        <Button
          variant="outlined"
          startIcon={<AiOutlineUserAdd />}
          onClick={() => {
            setDialogOpen(true)
          }}
        >
          Add new employee
        </Button>
      </div>
      <div className="DataGrid">
        <DataGrid
          page={page}
          paginationMode="server"
          rows={employeesData}
          columns={columns}
          pageSize={pageSize}
          onPageSizeChange={(newPageSize) => {
            setPageSize(newPageSize)
          }}
          onPageChange={(newPage) => setPage(newPage)}
          rowsPerPageOptions={[10, 20, 30]}
          rowCount={rowCountState}
          pagination
        />
      </div>

      <EmployeeFormDialog
        open={employeeDialogOpen}
        handleClose={() => {
          setDialogOpen(false)
        }}
        initialValues={{
          name: currentEmployee.name,
          surname: currentEmployee.surname,
          email: currentEmployee.email,
          address: currentEmployee.address,
          salary: currentEmployee.salary,
        }}
        id={!!currentEmployee ? currentEmployee.id : null}
        companyId={1}
        callback={refresh}
      />
      <ConfirmationDialog
        isOpen={confirmDialogOpen}
        handleClose={() => {
          setConfirmDialogOpen(false)
        }}
        handleAgree={async () => {
          await axios.delete('http://localhost:8080/employee', {
            data: {employeeId: currentEmployee.id},
          })
          setConfirmDialogOpen(false)
          refresh()
        }}
      />
    </Layout>
  )
}

export default Company
