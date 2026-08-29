import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createRouter, createWebHistory } from 'vue-router'

import App from './App.vue'
import StartPage from './pages/StartPage.vue'
import MainPage from './pages/MainPage.vue'
import { useAuthStore } from './stores/AuthStore.js'


const routes = [
  { path: '/', name: 'start', component: StartPage },
  { path: '/app', name: 'main', component: MainPage, meta: { requiresAuth: true } }
]


const router = createRouter({
  history: createWebHistory(),
  routes
})

const pinia = createPinia()

const app = createApp(App)

app.use(pinia)
app.use(router)

router.beforeEach(async (to) => {
  const authStore = useAuthStore()

  if (to.meta.requiresAuth) {
    const isAuth = await authStore.check_auth()
    if (!isAuth) {
      return { name: 'start' } 
    }
  }

  return true
})


app.mount('#app')
