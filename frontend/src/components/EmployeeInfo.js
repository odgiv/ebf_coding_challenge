import * as React from 'react'
import {useParams} from 'react-router-dom'
import Layout from './Layout'
import axios from 'axios'
import {Divider} from '@mui/material'

function EmployeeInfo() {
  const [data, setData] = React.useState({})
  const {employeeId} = useParams()

  React.useEffect(() => {
    async function fetchData() {
      const {data} = await axios.get('http://localhost:8080/employee', {params: {employeeId}})
      setData(data)
    }
    fetchData()
  }, [employeeId])

  const Entry = ({label, value}) => {
    return (
      <div className="mb-5">
        <div className="font-medium mb-2">{label}</div>
        <div>{value}</div>
        <Divider></Divider>
      </div>
    )
  }

  return (
    <Layout headerTitle="Employee info">
      <div className="grid grid-cols-2 gap-x-5">
        <>
          <Entry label="NAME" value={data.name}></Entry>
          <Entry label="SURNAME" value={data.surname}></Entry>
          <Entry label="EMAIL" value={data.email}></Entry>
          <Entry label="ADDRESS" value={data.address}></Entry>
          <Entry label="SALARY" value={data.salary}></Entry>
          <Entry label="COMPANY" value={data.company ? data.company.name : ''}></Entry>
        </>
      </div>
    </Layout>
  )
}

export default EmployeeInfo
