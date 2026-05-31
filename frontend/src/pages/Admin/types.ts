export interface DashboardAdmin {

  totalUsuarios: number

  totalAdministradores: number

  usuariosBloqueados: number

  registrosHoje: number

  totalBackups: number

  totalLogs: number
}

export interface Usuario {

  id: number

  nome: string

  email: string

  role: string

  genero?: string

  dataNascimento?: string

  bloqueadoAte?: string
}

export interface LogSistema {

  id: number

  dataHora: string

  tipo: string

  usuario: string

  descricao: string
}