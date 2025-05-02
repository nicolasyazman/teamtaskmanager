<template>
  <div class="p-10 min-h-screen bg-gray-50">
    <h1 class="text-3xl font-bold mb-4">Welcome to your Dashboard!</h1>
    <p class="mb-6 text-gray-600">This is your private space where you can manage your projects.</p>

    <!-- Add Project Button -->
    <button
      @click="showModal = true"
      class="mb-8 inline-flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-5 py-2.5 rounded shadow-md transition"
    >
      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
        stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M12 4v16m8-8H4" />
      </svg>
      Add Project
    </button>

    <!-- Project Cards Grid -->
    <div v-if="projects.length > 0">
      <h2 class="text-xl font-semibold mb-4 text-gray-700">Your Projects</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        <div
          v-for="project in projects"
          :key="project.id"
          class="aspect-square bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-md p-4 flex items-center justify-center text-center cursor-pointer transition"
        >
          <h3 class="text-lg font-semibold text-gray-800 break-words">{{ project.name }}</h3>
        </div>
      </div>
    </div>
    <div v-else class="text-gray-500 mt-10">
      <p>You have no projects yet.</p>
    </div>

    <!-- Modal Overlay -->
    <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-md">
        <h2 class="text-xl font-bold mb-4">Add New Project</h2>
        <form @submit.prevent="submitProject">
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">Project Name</label>
            <input v-model="newProject.name" type="text" required class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:border-blue-500" />
          </div>
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-1">Description</label>
            <textarea v-model="newProject.description" class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring focus:border-blue-500"></textarea>
          </div>
          <div class="flex justify-end gap-2">
            <button type="button" @click="showModal = false" class="bg-gray-300 hover:bg-gray-400 text-gray-700 px-4 py-2 rounded">
              Cancel
            </button>
            <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded">
              Create
            </button>
          </div>
        </form>
      </div>
    </div>

    <button
      @click="logout"
      class="mt-10 bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded transition"
    >
      Logout
    </button>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const projects = ref([])
const showModal = ref(false)
const newProject = ref({ name: '', description: '' })

const logout = () => {
  localStorage.removeItem('authToken')
  router.push('/login')
}

const fetchProjects = async () => {
  const token = localStorage.getItem('authToken')
  if (!token) {
    router.push('/login')
    return
  }

  try {
    const response = await axios.get('http://localhost:8080/project/mine', {
      headers: { Authorization: `Bearer ${token}` }
    })
    projects.value = response.data
  } catch (error) {
    console.error('Failed to fetch projects:', error)
    if (error.response?.status === 401) logout()
  }
}

const submitProject = async () => {
  const token = localStorage.getItem('authToken')
  if (!token) return router.push('/login')

  try {
    await axios.post('http://localhost:8080/project', newProject.value, {
      headers: { Authorization: `Bearer ${token}` }
    })
    newProject.value = { name: '', description: '' }
    showModal.value = false
    await fetchProjects()
  } catch (error) {
    console.error('Failed to create project:', error)
  }
}

onMounted(fetchProjects)
</script>
