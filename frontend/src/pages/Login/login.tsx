import './login.css'

import {
  useState
} from 'react'

import {
  useNavigate,
  Link
} from 'react-router-dom'

import {
  useAuth
} from '../../context/AuthContext'

function Login() {

  const navigate =
    useNavigate()

  const { login } =
    useAuth()

  const [email, setEmail] =
    useState('')

  const [senha, setSenha] =
    useState('')

  const [erro, setErro] =
    useState('')

  async function handleLogin(
    e: any
  ) {

    e.preventDefault()

    setErro('')

    try {

      const resposta =
        await login(
          email,
          senha
        )

      /*
        resposta esperada:
        {
          sucesso: true/false,
          mensagem: string
        }
      */

      if (!resposta.sucesso) {

        setErro(
          resposta.mensagem
        )

        return
      }

      navigate('/dashboard')

    } catch (error: any) {

      setErro(
        error.response?.data ||
        'Erro ao realizar login'
      )
    }
  }

  return (

    <div className="login-container">

      <div className="login-left">

        <div className="brand">

          <div className="brand-logo">
            P
          </div>

          <h1>PontoCorp</h1>
        </div>

        <div className="login-content">

          <span className="welcome">
            Bem-vindo de volta 👋
          </span>

          <h2>
            Faça login na sua conta
          </h2>

          <p>
            Gerencie seus registros
            de ponto de forma simples.
          </p>

          <form
            className="login-form"
            onSubmit={handleLogin}
          >

            <div className="input-group">

              <label>Email</label>

              <input
                type="email"
                placeholder="Digite seu email"
                value={email}
                onChange={(e) =>
                  setEmail(
                    e.target.value
                  )
                }
                required
              />
            </div>

            <div className="input-group">

              <label>Senha</label>

              <input
                type="password"
                placeholder="Digite sua senha"
                value={senha}
                onChange={(e) =>
                  setSenha(
                    e.target.value
                  )
                }
                required
              />
            </div>

            {
              erro && (

                <span className="error-message">
                  {erro}
                </span>
              )
            }

            <button type="submit">
              Entrar
            </button>
          </form>

          <div className="register-link">

            Não possui conta?

            <Link to="/cadastro">
              Criar conta
            </Link>

          </div>
        </div>
      </div>

      <div className="login-right">

        <div className="glass-card">

          <div className="clock-circle">

            <div className="clock-inner">
              ⏰
            </div>
          </div>

          <h3>
            Controle inteligente
          </h3>

          <p>
            Segurança e praticidade
            para sua empresa.
          </p>
        </div>
      </div>
    </div>
  )
}

export default Login