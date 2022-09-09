import * as React from 'react'
import axios from 'axios'
import Layout from './components/Layout'
import {NavLink} from 'react-router-dom'
import {Typography} from '@mui/material'

function App() {
  const [companies, setCompanies] = React.useState([])

  React.useEffect(() => {
    async function fetchData(url, setFunc) {
      const {data} = await axios.get(url)
      setFunc(data)
    }

    fetchData('http://localhost:8080/company', setCompanies)
  }, [])

  return (
    <Layout headerTitle="List of companies">
      {companies.map((company, i) => {
        return (
          <div key={company.id}>
            <Typography>
              {i + 1}. <NavLink to={'/company/' + company.id}>{company.name}</NavLink>
            </Typography>
          </div>
        )
      })}
    </Layout>
  )
}

export default App
