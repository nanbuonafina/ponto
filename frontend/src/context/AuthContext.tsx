import {

  createContext,
  useContext,
  useEffect,
  useState

} from 'react'

import api from '../services/api'

interface User {

  id: number

  nome: string

  email: string

  role: string
}

interface LoginResponse {

  sucesso: boolean

  mensagem: string
}

interface AuthContextType {

  user: User | null

  token: string | null

  login: (
    email: string,
    senha: string
  ) => Promise<LoginResponse>

  logout: () => void
}

const AuthContext =
  createContext<AuthContextType>(
    {} as AuthContextType
  )

export function AuthProvider({
  children
}: any) {

  const [user, setUser] =
    useState<User | null>(null)

  const [token, setToken] =
    useState<string | null>(null)

  useEffect(() => {

    const tokenStorage =
      localStorage.getItem('token')

    const userStorage =
      localStorage.getItem('user')

    if (
      tokenStorage &&
      userStorage
    ) {

      setToken(tokenStorage)

      setUser(
        JSON.parse(userStorage)
      )

      api.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${tokenStorage}`
    }

  }, [])

  async function login(
    email: string,
    senha: string
  ): Promise<LoginResponse> {

    try {

      const response =
        await api.post(
          '/auth/login',
          {
            email,
            senha
          }
        )

      const data = response.data

      setUser(data)

      setToken(data.token)

      localStorage.setItem(
        'token',
        data.token
      )

      localStorage.setItem(
        'user',
        JSON.stringify(data)
      )

      api.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${data.token}`

      return {

        sucesso: true,

        mensagem:
          'Login realizado com sucesso'
      }

    } catch (error: any) {

      console.error(error)

      return {

        sucesso: false,

        mensagem:

          error.response?.data ||

          'Email ou senha inválidos'
      }
    }
  }

  function logout() {

    setUser(null)

    setToken(null)

    localStorage.removeItem('token')

    localStorage.removeItem('user')

    delete api.defaults.headers.common[
      'Authorization'
    ]
  }

  return (

    <AuthContext.Provider
      value={{
        user,
        token,
        login,
        logout
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {

  return useContext(AuthContext)
}