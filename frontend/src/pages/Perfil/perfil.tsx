import './perfil.css'

import {
  useEffect,
  useState
} from 'react'

import {
  useNavigate
} from 'react-router-dom'

import api from '../../services/api'

function Perfil() {

  const navigate =
    useNavigate()

  const [editando, setEditando] =
    useState(false)

  const [usuario, setUsuario] =
    useState<any>(null)

  async function carregarPerfil() {

    try {

      const response =
        await api.get('/usuarios/me')

      setUsuario(response.data)

    } catch (error) {

      console.error(error)
    }
  }

  async function salvarPerfil() {

    try {

      await api.put(
        '/usuarios/me',
        {
          nome: usuario.nome,
          genero: usuario.genero,
          dataNascimento:
            usuario.dataNascimento
        }
      )

      alert(
        'Perfil atualizado com sucesso!'
      )

      setEditando(false)

      carregarPerfil()

    } catch (error) {

      console.error(error)

      alert(
        'Erro ao atualizar perfil'
      )
    }
  }

  useEffect(() => {

    carregarPerfil()

  }, [])

  if (!usuario) {

    return <p>Carregando...</p>
  }

  return (

    <div className="perfil-container">

      <div className="perfil-card">

        <div className="perfil-header">

          <div className="perfil-avatar">
            👤
          </div>

          <h1>
            Meu Perfil
          </h1>

          <p>
            Informações da conta
          </p>

        </div>

        <div className="perfil-info">

          <div className="info-item">

            <span>Nome</span>

            {editando ? (

              <input
                value={usuario.nome || ''}
                onChange={(e) =>
                  setUsuario({
                    ...usuario,
                    nome: e.target.value
                  })
                }
              />

            ) : (

              <strong>
                {usuario.nome}
              </strong>

            )}

          </div>

          <div className="info-item">

            <span>Email</span>

            <strong>
              {usuario.email}
            </strong>

          </div>

          <div className="info-item">

            <span>Perfil</span>

            <strong>
              {usuario.role}
            </strong>

          </div>

          <div className="info-item">

            <span>Gênero</span>

            {editando ? (

              <select
                value={
                  usuario.genero || ''
                }
                onChange={(e) =>
                  setUsuario({
                    ...usuario,
                    genero: e.target.value
                  })
                }
              >

                <option value="">
                  Selecione
                </option>

                <option value="Masculino">
                  Masculino
                </option>

                <option value="Feminino">
                  Feminino
                </option>

                <option value="Outro">
                  Outro
                </option>

              </select>

            ) : (

              <strong>
                {usuario.genero ||
                  'Não informado'}
              </strong>

            )}

          </div>

          <div className="info-item">

            <span>
              Data de nascimento
            </span>

            {editando ? (

              <input
                type="date"
                value={
                  usuario.dataNascimento ||
                  ''
                }
                onChange={(e) =>
                  setUsuario({
                    ...usuario,
                    dataNascimento:
                      e.target.value
                  })
                }
              />

            ) : (

              <strong>

                {usuario.dataNascimento
                  ? new Date(
                      usuario.dataNascimento
                    ).toLocaleDateString()
                  : 'Não informado'}

              </strong>

            )}

          </div>

        </div>

        <div className="perfil-actions">

          {!editando ? (

            <button
              className="editar-button"
              onClick={() =>
                setEditando(true)
              }
            >
              Editar Perfil
            </button>

          ) : (

            <button
              className="editar-button"
              onClick={salvarPerfil}
            >
              Salvar
            </button>

          )}

          <button
            className="senha-button"
            onClick={() =>
              navigate('/alterar-senha')
            }
          >
            Alterar senha
          </button>

          <button
            className="voltar-button"
            onClick={() =>
              navigate('/dashboard')
            }
          >
            Voltar
          </button>

        </div>

      </div>

    </div>
  )
}

export default Perfil