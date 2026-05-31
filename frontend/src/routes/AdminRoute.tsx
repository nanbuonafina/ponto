import { Navigate } from 'react-router-dom'

import { useAuth } from '../context/AuthContext'

import type { ReactNode } from 'react'

interface Props {
  children: ReactNode
}

export default function AdminRoute({
  children
}: Props) {

  const {
    user,
    loading
  } = useAuth()

  if (loading) {

    return (
      <div>
        Carregando...
      </div>
    )
  }

  if (!user) {

    return <Navigate to="/" />
  }

  if (user.role !== 'ADMIN') {

    return <Navigate to="/dashboard" />
  }

  return children
}