import './AdminDashboard.css'

import DashboardCards
from './components/DashboardCards'

import UsuariosSection
from './sections/UsuariosSection'

import LogsSection
from './sections/LogsSection'

import BackupSection
from './sections/BackupSection'

import RelatoriosSection
from './sections/RelatoriosSection'

export default function AdminDashboard() {

  return (

    <div className="admin-page">

      <header className="admin-header">

        <div className="admin-title">

          <h1>
            Painel Administrativo
          </h1>

          <p>
            Gestão completa do sistema
          </p>

        </div>

        <div className="admin-actions">

          <button
            className="admin-btn"
            onClick={() =>
              window.location.href =
                '/dashboard'
            }
          >
            Voltar ao Sistema
          </button>

        </div>

      </header>

      <section className="dashboard-area">

        <DashboardCards />

      </section>

      <section className="admin-layout">

        <div className="backup-column">

          <BackupSection />

        </div>

        <div className="usuarios-column">

          <UsuariosSection />
          <RelatoriosSection />

        </div>

        <div className="logs-column">

          <LogsSection />

        </div>

      </section>

    </div>
  )
}