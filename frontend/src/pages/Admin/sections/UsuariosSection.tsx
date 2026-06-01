import { useEffect, useState } from 'react'
import api from '../../../services/api'
import './UsuariosSection.css'

export default function UsuariosSection() {

  const [usuarios, setUsuarios] =
    useState<any[]>([])

  const [nome, setNome] =
    useState('')

  const [email, setEmail] =
    useState('')

  const [role, setRole] =
    useState('')
  
  const [usuarioSelecionado, setUsuarioSelecionado] =
    useState<any>(null)

  const [mostrarModal, setMostrarModal] =
    useState(false)

  const [formEditar, setFormEditar] =
    useState({
      nome: '',
      email: '',
      role: ''
    })

  async function carregarUsuarios() {

    try {

      const params: any = {}

      if (nome.trim()) {
        params.nome = nome
      }

      if (email.trim()) {
        params.email = email
      }

      if (role.trim()) {
        params.role = role
      }

      const response =
        await api.get(
          '/admin/usuarios',
          { params }
        )

      setUsuarios(response.data)

    } catch (error) {
      console.error(error)
    }
  }

  function abrirModal(usuario: any) {
    setUsuarioSelecionado(usuario)

    setFormEditar({
      nome: usuario.nome,
      email: usuario.email,
      role: usuario.role
    })

    setMostrarModal(true)
  }

  function fecharModal() {
    setMostrarModal(false)

    setUsuarioSelecionado(null)
  }

  async function salvarEdicao() {
    try {

      await api.put(
        `/admin/usuarios/${usuarioSelecionado.id}`,
        formEditar
      )

      await carregarUsuarios()

      fecharModal()

      alert('Usuário atualizado com sucesso!')

    } catch (error) {

      console.error(error)

      alert('Erro ao atualizar usuário')
    }
  }

  async function excluirUsuario(id: number) {

    const confirmar =
      window.confirm(
        'Deseja realmente excluir este usuário?'
      )

    if (!confirmar) return

    try {

      await api.delete(
        `/admin/usuarios/${id}`
      )

      carregarUsuarios()

    } catch (error) {
      console.error(error)
    }
  }

  useEffect(() => {
    carregarUsuarios()
  }, [])

  return (

    <div className="usuarios-section">

      <h2>Usuários</h2>

      <div className="filtros">

        <input
          placeholder="Nome"
          value={nome}
          onChange={(e) =>
            setNome(e.target.value)
          }
        />

        <input
          placeholder="Email"
          value={email}
          onChange={(e) =>
            setEmail(e.target.value)
          }
        />

        <select
          value={role}
          onChange={(e) =>
            setRole(e.target.value)
          }
        >
          <option value="">
            Todos
          </option>

          <option value="ADMIN">
            ADMIN
          </option>

          <option value="FUNCIONARIO">
            FUNCIONARIO
          </option>

        </select>

        <button className="btn-primary"
          onClick={carregarUsuarios}
        >
          Filtrar
        </button>

      </div>
      
      <div className="table-wrapper">
          <table className="admin-table">
            <thead>

              <tr>

                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Role</th>
                <th>Ações</th>

              </tr>

            </thead>

            <tbody>

              {usuarios.map((usuario) => (

                <tr key={usuario.id}>

                  <td>{usuario.id}</td>

                  <td>{usuario.nome}</td>

                  <td>{usuario.email}</td>

                  <td>{usuario.role}</td>

                  <td>

                    <button
                      className="btn-edit"
                      onClick={() =>
                        abrirModal(usuario)
                      }
                    >
                      Editar
                    </button>

                    <button className="btn-delete"
                      onClick={() =>
                        excluirUsuario(
                          usuario.id
                        )
                      }
                    >
                      Excluir
                    </button>

                  </td>

                </tr>

              ))}

            </tbody>
          </table>
      </div>
      

      {mostrarModal && (
        <div className="modal-overlay">

          <div className="modal">

            <h3>Editar Usuário</h3>

            <input
              type="text"
              value={formEditar.nome}
              onChange={(e) =>
                setFormEditar({
                  ...formEditar,
                  nome: e.target.value
                })
              }
              placeholder="Nome"
            />

            <input
              type="email"
              value={formEditar.email}
              onChange={(e) =>
                setFormEditar({
                  ...formEditar,
                  email: e.target.value
                })
              }
              placeholder="Email"
            />

            <select
              value={formEditar.role}
              onChange={(e) =>
                setFormEditar({
                  ...formEditar,
                  role: e.target.value
                })
              }
            >
              <option value="FUNCIONARIO">
                Funcionário
              </option>

              <option value="ADMIN">
                Administrador
              </option>
            </select>

            <div className="modal-actions">

              <button
                className="btn-save"
                onClick={salvarEdicao}
              >
                Salvar
              </button>

              <button
                className="btn-cancel"
                onClick={fecharModal}
              >
                Cancelar
              </button>

            </div>

          </div>

        </div>
      )}
    </div>
  )
}