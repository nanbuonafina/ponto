import axios from 'axios'

const api = axios.create({

  baseURL: 'https://192.168.1.14:8443'
})

export default api