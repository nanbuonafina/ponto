import './Sidebar.css'

interface Props {
  aba: string
  setAba: (aba: string) => void
}

function Sidebar({
  aba,
  setAba
}: Props) {

  return (

    <aside className="sidebar">

      <h2>PontoCorp</h2>

      <button
        className={
          aba === 'dashboard'
            ? 'active'
            : ''
        }
        onClick={() =>
          setAba('dashboard')
        }
      >
        Dashboard
      </button>

      <button
        className={
          aba === 'usuarios'
            ? 'active'
            : ''
        }
        onClick={() =>
          setAba('usuarios')
        }
      >
        Usuários
      </button>

      <button
        className={
          aba === 'logs'
            ? 'active'
            : ''
        }
        onClick={() =>
          setAba('logs')
        }
      >
        Logs
      </button>

      <button
        className={
          aba === 'backup'
            ? 'active'
            : ''
        }
        onClick={() =>
          setAba('backup')
        }
      >
        Backup
      </button>

      <button
        onClick={() =>
          window.location.href =
            '/dashboard'
        }
      >
        Voltar ao Sistema
      </button>

    </aside>
  )
}

export default Sidebar