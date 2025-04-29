<template>
    <div class="max-w-md mx-auto mt-10 p-6 bg-white shadow-lg rounded-xl">
      <h2 class="text-2xl font-bold mb-6 text-center">Create a new account</h2>
      
      <form @submit.prevent="submitForm" class="space-y-4">
        <div>
          <label class="block mb-1">Username</label>
          <input v-model="user.username" type="text" required class="w-full border rounded px-3 py-2" />
        </div>
  
        <div>
          <label class="block mb-1">Email</label>
          <input v-model="user.email" type="email" required class="w-full border rounded px-3 py-2" />
        </div>
  
        <div>
          <label class="block mb-1">Password</label>
          <input v-model="user.password" type="password" required class="w-full border rounded px-3 py-2" />
        </div>
  
        <button
          type="submit"
          class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded transition"
        >
          Create account now
        </button>
      </form>
  
      <p v-if="successMessage" class="mt-4 text-green-600">{{ successMessage }}</p>
      <p v-if="errorMessage" class="mt-4 text-red-600">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import axios from 'axios'
  
  const user = ref({
    username: '',
    email: '',
    password: ''
  })
  
  const successMessage = ref('')
  const errorMessage = ref('')
  
  const submitForm = async () => {
    successMessage.value = ''
    errorMessage.value = ''
  
    try {
      await axios.post('http://localhost:8080/api/user', user.value)
      successMessage.value = 'User created successfully!'
      user.value = { username: '', email: '', password: '' } // reset form
    } catch (err) {
      errorMessage.value = 'Error: ' + (err.response?.data?.message || err.message)
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