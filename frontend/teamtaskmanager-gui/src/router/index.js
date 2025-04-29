import { createRouter, createWebHistory } from 'vue-router'
import LandingPage from '../views/LandingPage.vue'
import UserRegistrationForm from '../views/UserRegistrationForm.vue'
import LoginPage from '../views/LoginPage.vue'
import Dashboard from '../views/Dashboard.vue'

const routes = [
    { path: '/', component: LandingPage },
    { path: '/register', component: UserRegistrationForm },
    { path: '/login', component: LoginPage },
    { path: '/dashboard', 
        component: Dashboard,
        beforeEnter: (to, from, next) => {
            // Let's check if the user has a token stored in localstorage
            const token = localStorage.getItem('authToken');
            if (token) {
                next()
            } else {
                next('/login')
            }

        }
    },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router