import { createRouter, createWebHistory } from 'vue-router'
import LandingPage from '../views/LandingPage.vue'
import UserRegistrationForm from '../views/UserRegistrationForm.vue'
import LoginPage from '../views/LoginPage.vue'

const routes = [
    { path: '/', component: LandingPage },
    { path: '/register', component: UserRegistrationForm },
    { path: '/login', component: LoginPage } 
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router