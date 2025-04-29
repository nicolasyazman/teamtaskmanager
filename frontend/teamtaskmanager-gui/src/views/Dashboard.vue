<template>
    <div class="p-10">
      <h1 class="text-3xl font-bold mb-4">Welcome to your Dashboard!</h1>
      <p>This is your private space where you can manage your tasks and projects.</p>
      <button @click="logout" class="mt-6 bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded">
        Logout
      </button>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  const logout = () => {
    // Clear the JWT token from localStorage
    localStorage.removeItem('authToken')
    // Redirect to login page
    router.push('/login')
  }
  </script>
  