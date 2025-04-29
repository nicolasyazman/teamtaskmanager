<template>
    <div class="min-h-screen flex flex-col items-center justify-center bg-gradient-to-br from-blue-50 to-blue-100 p-4">
      <h1 class="text-4xl font-bold text-blue-700 mb-6">Welcome to Task Management App</h1>
      <p class="text-gray-600 mb-4 text-center max-w-md">
        Manage your projects, tasks, and collaborations with ease. Powered by Spring Boot + VueJS.
      </p>
      <button
        @click="callBackend"
        class="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-3 rounded-lg shadow transition"
      >
        Test Backend API
      </button>
  
      <div v-if="response" class="mt-6 text-green-600 font-medium">
        ✅ Backend says: {{ response }}
      </div>
      <div v-if="error" class="mt-6 text-red-600 font-medium">
        ❌ Error: {{ error }}
      </div>

      <button
  @click="loginUser"
  class="mt-4 bg-green-600 hover:bg-green-700 text-white font-semibold px-6 py-3 rounded-lg shadow transition"
>
  Login as demo@example.com
</button>

<div v-if="loginMessage" class="mt-4 text-blue-700 font-medium">
  🔐 Login result: {{ loginMessage }}
</div>

    </div>

    <UserForm></UserForm>
  </template>
  
  <script setup>
  
  import { ref } from 'vue'
  import axios from 'axios'
  import UserForm from '../components/UserForm.vue'

  const response = ref(null)
  const error = ref(null)
  
  const callBackend = async () => {
    response.value = null
    error.value = null
    try {
      const res = await axios.get('http://localhost:8080/api/user')
      response.value = res.data
    } catch (err) {
      error.value = err.response?.data || err.message
    }
  }

  const loginMessage = ref(null)

const loginUser = async () => {
  loginMessage.value = null
  try {
    const res = await axios.post('http://localhost:8080/api/auth/login', {
      email: 'nicolas@mygmail.com',
      password: 'root'
    })
    loginMessage.value = res.data.message || 'Login successful'
  } catch (err) {
    loginMessage.value = 'Login failed: ' + (err.response?.data?.message || err.message)
  }
}
  </script>