import React, { useState } from 'react';
import api from '../services/api';

interface RegisterProps {
  onSwitchToLogin: () => void;
}

export default function Register({ onSwitchToLogin }: RegisterProps) {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [role, setRole] = useState('FUNCIONARIO'); // Valor padrão comum para sistemas de ponto
  const [sucesso, setSucesso] = useState(false);
  const [erro, setErro] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro('');
    setSucesso(false);

    try {
      // Envia o payload idêntico ao esperado pelo @RequestBody Usuario do Spring
      await api.post('/auth/register', { nome, email, senha, role });
      setSucesso(true);
      // Limpa o formulário
      setNome('');
      setEmail('');
      setSenha('');
    } catch (err: any) {
      setErro('Erro ao realizar o cadastro. Verifique os dados ou a conexão.');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="max-w-md w-full bg-white rounded-lg shadow-md p-8">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">Criar Conta</h2>

        {sucesso && (
          <div className="bg-green-100 text-green-700 p-3 rounded mb-4 text-sm text-center">
            Usuário cadastrado com sucesso!{' '}
            <button onClick={onSwitchToLogin} className="underline font-bold">Ir para o Login</button>
          </div>
        )}

        {erro && (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4 text-sm text-center">
            {erro}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Nome Completo</label>
            <input
              type="text"
              required
              className="mt-1 w-full p-2 border border-gray-300 rounded focus:ring-2 focus:ring-blue-500 focus:outline-none"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
            />
          </div>

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

          <div>
            <label className="block text-sm font-medium text-gray-700">Perfil (Role)</label>
            <select
              className="mt-1 w-full p-2 border border-gray-300 rounded focus:ring-2 focus:ring-blue-500 focus:outline-none"
              value={role}
              onChange={(e) => setRole(e.target.value)}
            >
              <option value="FUNCIONARIO">Funcionário</option>
              <option value="ADMIN">Administrador / RH</option>
            </select>
          </div>

          <button
            type="submit"
            className="w-full bg-green-600 text-white p-2 rounded font-semibold hover:bg-green-700 transition"
          >
            Cadastrar
          </button>
        </form>

        <p className="mt-4 text-center text-sm text-gray-600">
          Já tem uma conta?{' '}
          <button onClick={onSwitchToLogin} className="text-blue-600 hover:underline font-medium">
            Faça login
          </button>
        </p>
      </div>
    </div>
  );
}