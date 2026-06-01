import { useEffect, useState } from 'react'
import api from '../../../services/api'
import './RelatoriosSection.css'

export default function RelatoriosSection() {

  const [pontos, setPontos] =
    useState<any[]>([])

  useEffect(() => {

    carregar()

  }, [])

  async function carregar() {

    try {

      const response =
        await api.get(
          '/admin/pontos'
        )

      setPontos(response.data)

    } catch (error) {
      console.error(error)
    }
  }

  return (

    <div className="relatorios-section">

      <div className="relatorios-header">

        <h2>
          Relatório Geral de Pontos
        </h2>

        <span className="relatorios-count">
          {pontos.length} registros
        </span>

      </div>

      <div className="table-wrapper">

        <table className="admin-table">

          <thead>

            <tr>

              <th>Usuário</th>

              <th>Tipo</th>

              <th>Data</th>

            </tr>

          </thead>

          <tbody>

            {pontos.map((ponto) => (

              <tr key={ponto.id}>

                <td>
                  {ponto.usuario.nome}
                </td>

                <td>
                  {ponto.tipo}
                </td>

                <td>
                  {new Date(
                    ponto.dataHora
                  ).toLocaleString()}
                </td>

              </tr>

            ))}

          </tbody>

        </table>

      </div>

    </div>
  )
}