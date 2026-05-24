import './perfil.css'

import {

  useNavigate

} from 'react-router-dom'

function Perfil() {

  const navigate =
    useNavigate()

  const usuario =
    JSON.parse(
      localStorage.getItem('user') || '{}'
    )

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

            <span>
              Nome
            </span>

            <strong>
              {usuario.nome}
            </strong>
          </div>

          <div className="info-item">

            <span>
              Email
            </span>

            <strong>
              {usuario.email}
            </strong>
          </div>

          <div className="info-item">

            <span>
              Perfil
            </span>

            <strong>
              {usuario.role}
            </strong>
          </div>
        </div>

        <div className="perfil-actions">

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