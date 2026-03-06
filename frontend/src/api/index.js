import axios from 'axios'
import { ElMessage } from 'element-plus'

// 基础配置
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未授权，跳转到登录页
          window.location.href = '/login'
          break
        case 403:
          // 禁止访问
          ElMessage.error('无权限访问')
          break
        case 404:
          // 资源不存在
          ElMessage.error('资源不存在')
          break
        case 500:
          // 服务器错误
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error('请求失败')
      }
    }
    return Promise.reject(error)
  }
)

// 认证相关API
export const authApi = {
  register: (data) => api.post('/auth/register', data),
  login: (data) => api.post('/auth/login', data),
  getInfo: () => api.get('/auth/info')
}

// 岗位相关API
export const jobApi = {
  getJobs: (params) => api.get('/jobs', { params }),
  getJob: (id) => api.get(`/jobs/${id}`),
  createJob: (data) => api.post('/jobs', data),
  updateJob: (id, data) => api.put(`/jobs/${id}`, data),
  deleteJob: (id) => api.delete(`/jobs/${id}`),
  searchJobs: (params) => api.get('/jobs/search', { params })
}

// 简历相关API
export const resumeApi = {
  getResumes: () => api.get('/resumes'),
  getResume: (id) => api.get(`/resumes/${id}`),
  createResume: (data) => api.post('/resumes', data),
  updateResume: (id, data) => api.put(`/resumes/${id}`, data),
  deleteResume: (id) => api.delete(`/resumes/${id}`),
  previewResume: (id) => api.post(`/resumes/${id}/preview`)
}

// 投递相关API
export const applicationApi = {
  createApplication: (data) => api.post('/applications', data),
  getApplications: () => api.get('/applications'),
  updateApplicationStatus: (id, status) => api.put(`/applications/${id}/status`, { status })
}

// 评价相关API
export const evaluationApi = {
  createEvaluation: (data) => api.post('/evaluations', data),
  getEvaluations: (params) => api.get('/evaluations', { params })
}

// 统计相关API
export const statisticsApi = {
  getEmploymentStatistics: () => api.get('/statistics/employment'),
  getRecruitmentStatistics: () => api.get('/statistics/recruitment')
}

export default api