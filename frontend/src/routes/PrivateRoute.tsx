import { Navigate } from 'react-router-dom'

import { useAuth } from '../context/AuthContext'

import type { ReactNode } from 'react'

interface Props {
  children: ReactNode
}

export default function PrivateRoute({
  children
}: Props) {

  const {
    token,
    loading
  } = useAuth()

  if (loading) {

    return (
      <div>
        Carregando...
      </div>
    )
  }

  if (!token) {

    return <Navigate to="/" />
  }

  return children
}