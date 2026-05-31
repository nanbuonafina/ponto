import { useEffect, useState } from 'react'
import api from '../../../services/api'

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

    <div className="section-container">

      <h2 className="section-title">
        Relatório Geral de Pontos
      </h2>

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
  )
}