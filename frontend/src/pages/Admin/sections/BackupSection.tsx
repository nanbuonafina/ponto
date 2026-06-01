import { useEffect, useState } from 'react'
import api from '../../../services/api'
import './BackupSection.css'

export default function BackupSection() {

  const [backups, setBackups] =
    useState<string[]>([])

  const [arquivo, setArquivo] =
    useState('')

  async function carregarBackups() {

    const response =
      await api.get(
        '/admin/backups'
      )

    setBackups(response.data)
  }

  async function gerarBackup() {

    await api.post(
      '/admin/backup'
    )

    carregarBackups()

    alert(
      'Backup realizado com sucesso!'
    )
  }

  async function restaurar() {

    if (!arquivo) return

    const confirmar =
      window.confirm(
        `Restaurar ${arquivo}?`
      )

    if (!confirmar) return

    await api.post(
      `/admin/restore?arquivo=${arquivo}`
    )

    alert(
      'Backup restaurado!'
    )
  }

  useEffect(() => {

    carregarBackups()

  }, [])

  return (
    <div className="backup-section">

      <h2>
        Backups
      </h2>

      <div className="backup-buttons">

        <button
          onClick={gerarBackup}
        >
          Gerar Backup
        </button>

      </div>

      <div className="backup-list">

        {backups.map((backup) => (

          <div
            key={backup}
            className="backup-item"
          >
            {backup}
          </div>

        ))}

      </div>

      <div className="backup-buttons">

        <select
          value={arquivo}
          onChange={(e) =>
            setArquivo(
              e.target.value
            )
          }
        >

          <option value="">
            Selecione um backup
          </option>

          {backups.map((backup) => (

            <option
              key={backup}
              value={backup}
            >
              {backup}
            </option>

          ))}

        </select>

        <button
          onClick={restaurar}
        >
          Restaurar Backup
        </button>

      </div>

    </div>
  )
}