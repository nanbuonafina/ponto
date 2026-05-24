import './alterarsenha.css'

import { useState } from 'react'

import { useNavigate } from 'react-router-dom'

import api from '../../services/api'

function AlterarSenha() {

  const navigate =
    useNavigate()

  const [senhaAtual, setSenhaAtual] =
    useState('')

  const [novaSenha, setNovaSenha] =
    useState('')

  const [confirmacao, setConfirmacao] =
    useState('')

  const [mensagem, setMensagem] =
    useState('')

  async function alterarSenha() {

    setMensagem('')

    if (novaSenha !== confirmacao) {

      setMensagem(
        'As senhas não coincidem'
      )

      return
    }

    try {

      const usuario =
        JSON.parse(
          localStorage.getItem('user') || '{}'
        )

      const response =
        await api.put(
          '/auth/alterar-senha',
          {
            email: usuario.email,
            senhaAtual,
            novaSenha
          }
        )

      setMensagem(response.data)

      setSenhaAtual('')
      setNovaSenha('')
      setConfirmacao('')

    } catch (error: any) {

      setMensagem(

        error.response?.data ||

        'Erro ao alterar senha'
      )
    }
  }

  return (

    <div className="senha-container">

      <div className="senha-card">

        <h1>
          Alterar Senha
        </h1>

        <input
          type="password"
          placeholder="Senha atual"
          value={senhaAtual}
          onChange={(e) =>
            setSenhaAtual(e.target.value)
          }
        />

        <input
          type="password"
          placeholder="Nova senha"
          value={novaSenha}
          onChange={(e) =>
            setNovaSenha(e.target.value)
          }
        />

        <input
          type="password"
          placeholder="Confirmar nova senha"
          value={confirmacao}
          onChange={(e) =>
            setConfirmacao(e.target.value)
          }
        />

        <button onClick={alterarSenha}>
          Salvar nova senha
        </button>

        {
          mensagem && (
            <p className="mensagem">
              {mensagem}
            </p>
          )
        }

        <button
          className="voltar"
          onClick={() =>
            navigate('/perfil')
          }
        >
          Voltar
        </button>
      </div>
    </div>
  )
}

export default AlterarSenha