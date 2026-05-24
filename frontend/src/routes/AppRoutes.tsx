import { Routes, Route } from 'react-router-dom'

import Login from '../pages/Login/login'

import Register from '../pages/Register/register'

import DashboardFuncionario
from '../pages/Dashboard/dashboardfuncionario'

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

    </Routes>
  )
}

export default AppRoutes