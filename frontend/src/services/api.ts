import axios from 'axios';

const api = axios.create({
  baseURL: 'https://192.168.1.4:8443/', // URL do seu Spring Boot
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;