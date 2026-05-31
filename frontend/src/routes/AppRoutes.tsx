import { Routes, Route } from 'react-router-dom'

import Login from '../pages/Login/login'

import Register from '../pages/Register/register'

import DashboardFuncionario
from '../pages/Dashboard/dashboardfuncionario'

import Perfil
from '../pages/Perfil/perfil'

import AlterarSenha
from '../pages/AlterarSenha/alterarsenha'

import AdminDashboard
from '../pages/Admin/AdminDashboard'

import PrivateRoute
from './PrivateRoute'

import AdminRoute
from './AdminRoute'

function AppRoutes() {

  return (

    <Routes>

      <Route
        path="/"
        element={<Login />}
      />

      <Route
        path="/cadastro"
        element={<Register />}
      />

      <Route
        path="/dashboard"
        element={
          <PrivateRoute>
            <DashboardFuncionario />
          </PrivateRoute>
        }
      />

      <Route
        path="/perfil"
        element={
          <PrivateRoute>
            <Perfil />
          </PrivateRoute>
        }
      />

      <Route
        path="/alterar-senha"
        element={
          <PrivateRoute>
            <AlterarSenha />
          </PrivateRoute>
        }
      />

      <Route
        path="/admin"
        element={
          <AdminRoute>
            <AdminDashboard />
          </AdminRoute>
        }
      />

    </Routes>
  )
}

export default AppRoutes