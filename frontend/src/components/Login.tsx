import { useState } from 'react';
import api from '../services/api';

interface LoginProps {
  onSwitchToRegister: () => void;
  onLoginSuccess: (userData: any) => void;
}

export default function Login({ onSwitchToRegister, onLoginSuccess }: LoginProps) {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erro, setErro] = useState('');
  const [carregando, setCarregando] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro('');
    setCarregando(true);

    try {
      const response = await api.post('/auth/login', { email, senha });
      // Retorna o LoginResponseDTO (id, nome, email, role)
      onLoginSuccess(response.data);
    } catch (err: any) {
      if (err.response && err.response.status === 401) {
        setErro('E-mail ou senha incorretos.');
      } else {
        setErro('Erro ao conectar com o servidor. Tente mais tarde.');
      }
    } finally {
      setCarregando(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="max-w-md w-full bg-white rounded-lg shadow-md p-8">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">Ponto Eletrônico - Login</h2>
        
        {erro && (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4 text-sm text-center">
            {erro}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">E-mail</label>
            <input
              type="email"
              required
              className="mt-1 w-full p-2 border border-gray-300 rounded focus:ring-2 focus:ring-blue-500 focus:outline-none"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Senha</label>
            <input
              type="password"
              required
              className="mt-1 w-full p-2 border border-gray-300 rounded focus:ring-2 focus:ring-blue-500 focus:outline-none"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
            />
          </div>

          <button
            type="submit"
            disabled={carregando}
            className="w-full bg-blue-600 text-white p-2 rounded font-semibold hover:bg-blue-700 transition disabled:bg-blue-400"
          >
            {carregando ? 'Entrando...' : 'Entrar'}
          </button>
        </form>

        <p className="mt-4 text-center text-sm text-gray-600">
          Não tem conta?{' '}
          <button onClick={onSwitchToRegister} className="text-blue-600 hover:underline font-medium">
            Cadastre-se aqui
          </button>
        </p>
      </div>
    </div>
  );
}