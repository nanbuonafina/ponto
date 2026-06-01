import { useEffect, useState } from 'react'
import api from '../../../services/api'
import './LogsSection.css'

export default function LogsSection() {

  const [logs, setLogs] =
    useState<any[]>([])

  const [tipo, setTipo] =
    useState('')

  const [usuario, setUsuario] =
    useState('')

  async function carregarLogs() {

    try {

      const response =
        await api.get(
          '/admin/logs/filtro',
          {
            params: {
              tipo,
              usuario
            }
          }
        )

      setLogs(response.data)

    } catch (error) {
      console.error(error)
    }
  }

  useEffect(() => {

    api.get('/admin/logs/recentes')
      .then((response) =>
        setLogs(response.data)
      )

  }, [])

  return (
    <div className="logs-section">

      <h2>
        Logs
      </h2>

      <div className="logs-filtros">

        <input
          placeholder="Usuário"
          value={usuario}
          onChange={(e) =>
            setUsuario(
              e.target.value
            )
          }
        />

        <select
          value={tipo}
          onChange={(e) =>
            setTipo(e.target.value)
          }
        >

          <option value="">
            Todos
          </option>

          <option value="LOGIN_SUCESSO">
            LOGIN_SUCESSO
          </option>

          <option value="LOGIN_FALHA">
            LOGIN_FALHA
          </option>

          <option value="REGISTRO_PONTO">
            REGISTRO_PONTO
          </option>

          <option value="BACKUP_MANUAL">
            BACKUP_MANUAL
          </option>

          <option value="BACKUP_AUTOMATICO">
            BACKUP_AUTOMATICO
          </option>

        </select>

        <button
          onClick={carregarLogs}
        >
          Filtrar
        </button>

      </div>

      {logs.map((log) => (

        <div
          key={log.id}
          className="log-item"
        >

          <strong>
            {log.tipo}
          </strong>

          <p>
            {log.usuario}
          </p>

          <p>
            {log.descricao}
          </p>

          <small>
            {new Date(
              log.dataHora
            ).toLocaleString()}
          </small>

        </div>

      ))}

    </div>
  )
}