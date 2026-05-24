import './register.css'

import { useState } from 'react'

import api from '../../services/api'

function Register() {

  const [nome, setNome] =
    useState('')

  const [email, setEmail] =
    useState('')

  const [senha, setSenha] =
    useState('')

  const [role, setRole] =
    useState('FUNCIONARIO')

  const [adminCode, setAdminCode] =
    useState('')

  const [erro, setErro] =
    useState('')

  async function cadastrar(
    e: any
  ) {

    e.preventDefault()

    try {

      await api.post(
        '/auth/register',
        {
          nome,
          email,
          senha,
          role,
          adminCode
        }
      )

      alert('Conta criada com sucesso!')

      window.location.href = '/'

    } catch (error: any) {

      console.error(error)

      setErro(
        'Erro ao criar conta'
      )
    }
  }

  return (

    <div className="register-container">

      {/* ESQUERDA */}

      <div className="register-left">

        <div className="brand">

          <div className="brand-icon">
            ⏰
          </div>

          <h1>
            PontoCorp
          </h1>
        </div>

        <div className="register-content">

          <span className="small-text">
            Comece agora 🚀
          </span>

          <h2>
            Criar conta
          </h2>

          <p>
            Gerencie seus registros
            de ponto de forma moderna.
          </p>

          <form
            className="register-form"
            onSubmit={cadastrar}
          >

            <div className="input-group">

              <label>
                Nome
              </label>

              <input
                type="text"
                placeholder="Seu nome"
                value={nome}
                onChange={(e) =>
                  setNome(e.target.value)
                }
              />
            </div>

            <div className="input-group">

              <label>
                Email
              </label>

              <input
                type="email"
                placeholder="voce@email.com"
                value={email}
                onChange={(e) =>
                  setEmail(e.target.value)
                }
              />
            </div>

            <div className="input-group">

              <label>
                Senha
              </label>

              <input
                type="password"
                placeholder="********"
                value={senha}
                onChange={(e) =>
                  setSenha(e.target.value)
                }
              />
            </div>

            <div className="role-selector">

              <button
                type="button"
                className={
                  role === 'FUNCIONARIO'
                    ? 'active'
                    : ''
                }
                onClick={() =>
                  setRole('FUNCIONARIO')
                }
              >
                Funcionário
              </button>

              <button
                type="button"
                className={
                  role === 'ADMIN'
                    ? 'active'
                    : ''
                }
                onClick={() =>
                  setRole('ADMIN')
                }
              >
                Admin
              </button>
            </div>

            {
              role === 'ADMIN' && (

                <div className="input-group">

                  <label>
                    Código Admin
                  </label>

                  <input
                    type="text"
                    placeholder="Código"
                    value={adminCode}
                    onChange={(e) =>
                      setAdminCode(
                        e.target.value
                      )
                    }
                  />
                </div>
              )
            }

            {
              erro && (
                <span className="erro">
                  {erro}
                </span>
              )
            }

            <button
              type="submit"
              className="register-button"
            >
              Criar conta
            </button>
          </form>

          <div className="login-link">

            Já possui conta?

            <a href="/">
              Fazer login
            </a>
          </div>
        </div>
      </div>

      {/* DIREITA */}

      <div className="register-right">

        <div className="glass-card">

          <div className="big-icon">
            ⏰
          </div>

          <h3>
            Sistema moderno de ponto
          </h3>

          <p>
            Segurança, praticidade
            e produtividade para
            sua empresa.
          </p>
        </div>
      </div>
    </div>
  )
}

export default Register