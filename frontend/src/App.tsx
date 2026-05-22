import { useState } from 'react';
import Login from './components/Login';
import Register from './components/Register';

type TelaAtual = 'LOGIN' | 'CADASTRO' | 'DASHBOARD';

export default function App() {
  const [tela, setTela] = useState<TelaAtual>('LOGIN');
  const [usuarioLogado, setUsuarioLogado] = useState<any>(null);

  const handleLoginSuccess = (userData: any) => {
    setUsuarioLogado(userData);
    setTela('DASHBOARD');
  };

  const handleLogout = () => {
    setUsuarioLogado(null);
    setTela('LOGIN');
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {tela === 'LOGIN' && (
        <Login 
          onSwitchToRegister={() => setTela('CADASTRO')} 
          onLoginSuccess={handleLoginSuccess}
        />
      )}

      {tela === 'CADASTRO' && (
        <Register onSwitchToLogin={() => setTela('LOGIN')} />
      )}

      {tela === 'DASHBOARD' && (
        <div className="p-8 max-w-4xl mx-auto">
          <div className="bg-white rounded-lg shadow p-6 flex justify-between items-center mb-6">
            <div>
              <h1 className="text-2xl font-bold text-gray-800">Olá, {usuarioLogado?.nome}!</h1>
              <p className="text-sm text-gray-500">Perfil: {usuarioLogado?.role}</p>
            </div>
            <button 
              onClick={handleLogout}
              className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition"
            >
              Sair
            </button>
          </div>
          
          <div className="bg-blue-50 border border-blue-200 rounded p-6 text-center">
            <h2 className="text-xl font-semibold text-blue-800 mb-4">Área de Batida de Ponto</h2>
            <p className="text-gray-600 mb-4">Você está autenticado com sucesso via Spring Boot!</p>
            {/* Aqui entrará seu componente futuro de bater ponto */}
            <button className="bg-blue-600 text-white px-6 py-3 rounded-lg font-bold shadow hover:bg-blue-700 transition">
              Registrar Ponto Atual
            </button>
          </div>
        </div>
      )}
    </div>
  );
}