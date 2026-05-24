import { Routes, Route } from 'react-router-dom'

import Login from '../pages/Login/login'

import Register from '../pages/Register/register'

import DashboardFuncionario
from '../pages/Dashboard/dashboardfuncionario'

import Perfil from '../pages/Perfil/perfil'

import AlterarSenha
from '../pages/AlterarSenha/alterarsenha'


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
        element={<DashboardFuncionario />}
      />

      <Route
        path="/perfil"
        element={<Perfil />}
      />

      <Route
        path="/alterar-senha"
        element={<AlterarSenha />}
      />

    </Routes>
  )
}

export default AppRoutes