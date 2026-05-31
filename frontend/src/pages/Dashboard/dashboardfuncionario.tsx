import './dashboardfuncionario.css'

import { useEffect, useState } from 'react'

import api from '../../services/api'

function DashboardFuncionario() {

  const [historico, setHistorico] =
    useState<any[]>([])

  const [todosPontos, setTodosPontos] =
    useState<any[]>([])

  const [mostrarTodos, setMostrarTodos] =
    useState(false)

  const [mensagem, setMensagem] =
    useState('')

  const usuario =
    JSON.parse(
      localStorage.getItem('user') || '{}'
    )

  const token =
    localStorage.getItem('token')

  async function carregarHistorico() {

    try {

      const response =
        await api.get(
          '/ponto/meus',
          {
            headers: {
              Authorization:
                `Bearer ${token}`
            }
          }
        )

      setHistorico(response.data)

    } catch (error) {

      console.error(error)
    }
  }

  async function carregarTodosPontos() {

    try {

      const response =
        await api.get(
          '/relatorios/todos',
          {
            headers: {
              Authorization:
                `Bearer ${token}`
            }
          }
        )

      setTodosPontos(response.data)
      setMostrarTodos(true)

    } catch (error) {

      console.error(error)
    }
  }

  async function registrarPonto() {

    try {

      await api.post(
        '/ponto/registrar',
        {},
        {
          headers: {
            Authorization:
              `Bearer ${token}`
          }
        }
      )

      setMensagem(
        'Ponto registrado com sucesso!'
      )

      carregarHistorico()

    } catch (error) {

      console.error(error)

      setMensagem(
        'Erro ao registrar ponto'
      )
    }
  }

  async function exportarExcel() {

    try {

      const response =
        await api.get(
          '/relatorios/meus',
          {
            headers: {
              Authorization:
                `Bearer ${token}`
            },
            responseType: 'blob'
          }
        )

      const url =
        window.URL.createObjectURL(
          new Blob([response.data])
        )

      const link =
        document.createElement('a')

      link.href = url

      link.setAttribute(
        'download',
        'meus-pontos.xlsx'
      )

      document.body.appendChild(link)

      link.click()

    } catch (error) {

      console.error(error)
    }
  }

  async function realizarBackup() {

    try {

      const response =
        await api.get(
          '/admin/backup',
          {
            headers: {
              Authorization:
                `Bearer ${token}`
            },
            responseType: 'blob'
          }
        )

      const url =
        window.URL.createObjectURL(
          new Blob([response.data])
        )

      const link =
        document.createElement('a')

      link.href = url

      link.setAttribute(
        'download',
        'backup.sql'
      )

      document.body.appendChild(link)

      link.click()

      setMensagem('Backup realizado!')

    } catch (error) {

      console.error(error)

      setMensagem(
        'Erro ao realizar backup'
      )
    }
  }

  async function restoreBanco(event: any) {

    try {

      const arquivo =
        event.target.files[0]

      if (!arquivo) return

      const formData =
        new FormData()

      formData.append(
        'file',
        arquivo
      )

      await api.post(
        '/admin/restore',
        formData,
        {
          headers: {
            Authorization:
              `Bearer ${token}`,
            'Content-Type':
              'multipart/form-data'
          }
        }
      )

      setMensagem('Restore realizado!')

    } catch (error) {

      console.error(error)

      setMensagem('Erro no restore')
    }
  }

  function logout() {

    localStorage.clear()

    window.location.href = '/'
  }

  useEffect(() => {

    carregarHistorico()

  }, [])

  return (

    <div className="dashboard">

      <header className="top-header">

        <div className="logo">
          PontoCorp
        </div>

        <div className="header-right">

          <button
            className="excel-btn"
            onClick={exportarExcel}
          >
            Exportar Excel
          </button>

          <button
            className="logout-btn"
            onClick={logout}
          >
            Sair
          </button>

        </div>
      </header>

      <main className="dashboard-content">

        <section className="main-card">

          <h1>Olá, {usuario.nome}</h1>

          <p>Registre seu ponto abaixo</p>

          <button
            className="ponto-btn"
            onClick={registrarPonto}
          >
            Bater Ponto
          </button>

          {mensagem && (
            <span className="mensagem">
              {mensagem}
            </span>
          )}

        </section>

        <section className="historico-card">

          <h2>Histórico</h2>

          <div className="historico-list">

            {historico.map((item) => (
              <div
                className="historico-item"
                key={item.id}
              >
                <strong>{item.tipo}</strong>

                <span>
                  {new Date(
                    item.dataHora
                  ).toLocaleString()}
                </span>
              </div>
            ))}

          </div>

        </section>

        {/* PAINEL ADMIN */}
        {usuario.role === 'ADMIN' && (
          <section className="admin-card">

            <h2>Painel Administrativo</h2>

            <div className="admin-actions">

              <button
                onClick={() =>
                  window.location.href = '/admin'
                }
              >
                Abrir Painel Administrativo
              </button>

            </div>

            {mostrarTodos && (
              <div className="admin-list">

                <h3>Registros gerais</h3>

                {todosPontos.map((item) => (
                  <div
                    className="historico-item"
                    key={item.id}
                  >
                    <strong>
                      {item.usuario.nome}
                    </strong>

                    <span>{item.tipo}</span>

                    <span>
                      {new Date(
                        item.dataHora
                      ).toLocaleString()}
                    </span>
                  </div>
                ))}

              </div>
            )}

          </section>
        )}

      </main>
    </div>
  )
}

export default DashboardFuncionario