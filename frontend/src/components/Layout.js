import * as React from 'react'
import {Typography} from '@mui/material'

function Layout({children, headerTitle}) {
  const classes = 'max-w-7xl mx-auto py-6'
  return (
    <>
      <header className="bg-white shadow sticky top-0 z-50">
        <div className={classes}>
          <Typography variant="h6">{headerTitle}</Typography>
        </div>
      </header>
      <main>
        <div className={classes}>{children}</div>
      </main>
    </>
  )
}

export default Layout
