import { useState } from 'react'

import Sidebar from './components/Sidebar'
import DashboardCards from './components/DashboardCards' 
import UsuariosSection from './sections/UsuariosSection'
import LogsSection from './sections/LogsSection'
import BackupSection from './sections/BackupSection'
import RelatoriosSection from './sections/RelatoriosSection'

function AdminDashboard() {

  const [aba, setAba] =
    useState('dashboard')

  return (

    <div
      style={{
        display: 'flex',
        minHeight: '100vh'
      }}
    >

      <Sidebar
        aba={aba}
        setAba={setAba}
      />

      <main
        style={{
          flex: 1,
          padding: '30px'
        }}
      >

        {aba === 'dashboard' && (
          <DashboardCards />
        )}

        {aba === 'usuarios' && (
          <UsuariosSection />
        )}

        {aba === 'logs' && (
          <LogsSection />
        )}

        {aba === 'backup' && (
          <BackupSection />
        )}

        {aba === 'relatorios' && (
          <RelatoriosSection />
        )}

      </main>

    </div>
  )
}

export default AdminDashboard