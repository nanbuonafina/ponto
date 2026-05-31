import { useEffect, useState } from 'react'
import api from '../../../services/api'

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

    <div className="section-container">

      <h2 className="section-title">Backups</h2>

      <button className="btn-primary"
        onClick={gerarBackup}
      >
        Gerar Backup
      </button>

      <hr />

      <select
        value={arquivo}
        onChange={(e) =>
          setArquivo(
            e.target.value
          )
        }
      >

        <option value="">
          Selecione
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

      <button className="btn-danger"
        onClick={restaurar}
      >
        Restaurar
      </button>

    </div>
  )
}