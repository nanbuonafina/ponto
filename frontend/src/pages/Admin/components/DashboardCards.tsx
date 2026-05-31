import { useEffect, useState } from 'react'

import api from '../../../services/api'

export default function DashboardCards() {

  const [dados, setDados] =
    useState<any>(null)

  useEffect(() => {

    carregarDashboard()

  }, [])

  async function carregarDashboard() {

    try {

      const response =
        await api.get(
          '/admin/dashboard'
        )

      setDados(response.data)

    } catch (error) {

      console.error(error)
    }
  }

  if (!dados) {

    return <p>Carregando...</p>
  }

  return (

    <div
      style={{
        display: 'grid',
        gridTemplateColumns:
          'repeat(auto-fit, minmax(220px,1fr))',
        gap: '20px'
      }}
    >

      <div className="card-admin">
        <h3>Usuários</h3>
        <p>{dados.totalUsuarios}</p>
      </div>

      <div className="card-admin">
        <h3>Administradores</h3>
        <p>{dados.totalAdministradores}</p>
      </div>

      <div className="card-admin">
        <h3>Bloqueados</h3>
        <p>{dados.usuariosBloqueados}</p>
      </div>

      <div className="card-admin">
        <h3>Pontos Hoje</h3>
        <p>{dados.registrosHoje}</p>
      </div>

      <div className="card-admin">
        <h3>Backups</h3>
        <p>{dados.totalBackups}</p>
      </div>

      <div className="card-admin">
        <h3>Logs</h3>
        <p>{dados.totalLogs}</p>
      </div>

    </div>
  )
}