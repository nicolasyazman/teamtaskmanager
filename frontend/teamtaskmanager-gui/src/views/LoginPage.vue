<template>
    <div class="max-w-md mx-auto mt-10 p-6 bg-white shadow-lg rounded-xl">
      <h2 class="text-2xl font-bold mb-6 text-center">Login</h2>
  
      <form @submit.prevent="login" class="space-y-4">
        <div>
          <label class="block mb-1">Email</label>
          <input v-model="email" type="email" required class="w-full border px-3 py-2 rounded" />
        </div>
  
        <div>
          <label class="block mb-1">Password</label>
          <input v-model="password" type="password" required class="w-full border px-3 py-2 rounded" />
        </div>
  
        <button
          type="submit"
          class="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded"
        >
          Login
        </button>
      </form>
  
      <p v-if="successMessage" class="mt-4 text-green-600">{{ successMessage }}</p>
      <p v-else class="mt-4 text-red-600">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import axios from 'axios'
  import { useRouter } from 'vue-router'

  const email = ref('')
  const password = ref('')
  const successMessage = ref('')
  const errorMessage = ref('')
  const router = useRouter() // To programmatically navigate after login

  const login = async () => {
    // Clear previous messages
    successMessage.value = ''
    errorMessage.value = ''
  
    try {
      // Send login request to the Spring Boot backend
      const response = await axios.post('/api/auth/login', {
        email: email.value,
        password: password.value
      })
  
      // Check if login is successful and display success message
      if (response.status === 200) {
        successMessage.value = 'Login successful! You are now logged in.'
        
        // Save the JWT token in localStorage
        localStorage.setItem('authToken', response.data.token)

        // Redirect the user to their dashboard
        router.push('/dashboard')

      }
    } catch (err) {
      // If login failed, display error message
      if (err.response) {
        errorMessage.value = 'Invalid credentials. Please try again.'
      } else {
        errorMessage.value = 'An unexpected error occurred. Please try again later.'
      }
    }
  }
  </script>
  
  <style scoped>
  input:focus {
    outline: none;
    border-color: #3b82f6;
    box-shadow: 0 0 0 1px #3b82f6;
  }
  </style>
  