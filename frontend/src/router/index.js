import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/student',
    name: 'StudentLayout',
    component: () => import('../views/student/Layout.vue'),
    children: [
      {
        path: 'home',
        name: 'StudentHome',
        component: () => import('../views/student/Home.vue')
      },
      {
        path: 'jobs',
        name: 'StudentJobs',
        component: () => import('../views/student/Jobs.vue')
      },
      {
        path: 'resume',
        name: 'StudentResume',
        component: () => import('../views/student/Resume.vue')
      },
      {
        path: 'applications',
        name: 'StudentApplications',
        component: () => import('../views/student/Applications.vue')
      },
      {
        path: 'favorites',
        name: 'StudentFavorites',
        component: () => import('../views/student/Favorites.vue')
      },
      {
        path: 'profile',
        name: 'StudentProfile',
        component: () => import('../views/student/Profile.vue')
      }
    ]
  },
  {
    path: '/company',
    name: 'CompanyLayout',
    component: () => import('../views/company/Layout.vue'),
    children: [
      {
        path: 'home',
        name: 'CompanyHome',
        component: () => import('../views/company/Home.vue')
      },
      {
        path: 'jobs',
        name: 'CompanyJobs',
        component: () => import('../views/company/Jobs.vue')
      },
      {
        path: 'resumes',
        name: 'CompanyResumes',
        component: () => import('../views/company/Resumes.vue')
      },
      {
        path: 'interviews',
        name: 'CompanyInterviews',
        component: () => import('../views/company/Interviews.vue')
      },
      {
        path: 'profile',
        name: 'CompanyProfile',
        component: () => import('../views/company/Profile.vue')
      }
    ]
  },
  {
    path: '/admin',
    name: 'AdminLayout',
    component: () => import('../views/admin/Layout.vue'),
    children: [
      {
        path: 'home',
        name: 'AdminHome',
        component: () => import('../views/admin/Home.vue')
      },
      {
        path: 'users',
        name: 'AdminUsers',
        component: () => import('../views/admin/Users.vue')
      },
      {
        path: 'companies',
        name: 'AdminCompanies',
        component: () => import('../views/admin/Companies.vue')
      },
      {
        path: 'statistics',
        name: 'AdminStatistics',
        component: () => import('../views/admin/Statistics.vue')
      },
      {
        path: 'settings',
        name: 'AdminSettings',
        component: () => import('../views/admin/Settings.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')
  
  // 不需要认证的路由
  if (to.path === '/login' || to.path === '/register') {
    next()
    return
  }
  
  // 需要认证的路由
  if (!token) {
    next('/login')
    return
  }
  
  // 检查角色权限
  if (to.path.startsWith('/student') && role !== 'student') {
    next('/login')
    return
  }
  if (to.path.startsWith('/company') && role !== 'company') {
    next('/login')
    return
  }
  if (to.path.startsWith('/admin') && role !== 'admin') {
    next('/login')
    return
  }
  
  next()
})

export default router