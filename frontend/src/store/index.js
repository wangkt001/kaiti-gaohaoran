import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || '',
    role: localStorage.getItem('role') || ''
  }),
  getters: {
    isLoggedIn: (state) => !!state.token,
    currentUser: (state) => state.user,
    currentRole: (state) => state.role
  },
  actions: {
    setUser(user) {
      this.user = user
    },
    setToken(token) {
      this.token = token
      localStorage.setItem('token', token)
    },
    setRole(role) {
      this.role = role
      localStorage.setItem('role', role)
    },
    logout() {
      this.user = null
      this.token = ''
      this.role = ''
      localStorage.removeItem('token')
      localStorage.removeItem('role')
    }
  }
})

export const useJobStore = defineStore('job', {
  state: () => ({
    jobs: [],
    currentJob: null,
    favorites: []
  }),
  getters: {
    jobList: (state) => state.jobs,
    currentJobInfo: (state) => state.currentJob,
    favoriteJobs: (state) => state.favorites
  },
  actions: {
    setJobs(jobs) {
      this.jobs = jobs
    },
    setCurrentJob(job) {
      this.currentJob = job
    },
    addFavorite(jobId) {
      if (!this.favorites.includes(jobId)) {
        this.favorites.push(jobId)
      }
    },
    removeFavorite(jobId) {
      this.favorites = this.favorites.filter(id => id !== jobId)
    }
  }
})

export const useApplicationStore = defineStore('application', {
  state: () => ({
    applications: []
  }),
  getters: {
    applicationList: (state) => state.applications
  },
  actions: {
    setApplications(applications) {
      this.applications = applications
    },
    addApplication(application) {
      this.applications.push(application)
    },
    updateApplicationStatus(id, status) {
      const application = this.applications.find(app => app.id === id)
      if (application) {
        application.status = status
      }
    }
  }
})