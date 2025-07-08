<template>
    <div class="overflow-x-auto" @click="handleClickOutside">
      <!-- Back to Dashboard Button -->
      <div class="mb-4">
        <button 
          @click="goToDashboard"
          class="px-4 py-2 bg-blue-600 text-white rounded-lg shadow-md hover:bg-blue-700 transition duration-300 ease-in-out">
          &#8592; Back to Dashboard
        </button>
      </div>
  
      <!-- Task Columns -->
      <div class="flex lg:grid lg:grid-cols-5 gap-4 min-w-[1000px] p-4">
  
        <!-- Backlog Column -->
        <div class="flex flex-col min-w-[200px]">
          <h2 class="text-xl font-bold mb-2 text-center text-blue-600">Backlog</h2>
          <div class="flex flex-col gap-4">
            <div v-for="task in backlogTasks" :key="task.id"
                 class="relative w-full h-48 bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-md p-4 flex items-center justify-center text-center cursor-pointer transition"
                 @click.stop="openTaskMenu(task)">
              <div class="text-lg font-semibold text-gray-800 break-words">
                {{ task.title }}
              </div>
              <!-- Contextual Menu for Task -->
              <div v-if="task.showMenu" class="absolute top-0 right-0 mt-2 mr-2 bg-white shadow-lg border border-gray-200 rounded-lg">
                <button @click="moveTaskToStatus(task, 'TODO')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to TODO</button>
                <button @click="moveTaskToStatus(task, 'PROGRESS')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to In Progress</button>
                <button @click="assignTask(task)" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Assign Task</button>
                <button @click="deleteTask(task)" class="px-4 py-2 text-sm text-red-600 hover:bg-red-100">Delete Task</button>
              </div>
            </div>
          </div>
          <button @click="addNewTask('BACKLOG')" class="mt-4 bg-blue-600 text-white rounded-full w-12 h-12 flex items-center justify-center text-2xl hover:bg-blue-700 transition duration-200">
            +
          </button>
        </div>
  
        <!-- To Do Column -->
        <div class="flex flex-col min-w-[200px]">
          <h2 class="text-xl font-bold mb-2 text-center text-yellow-600">To Do</h2>
          <div class="flex flex-col gap-4">
            <div v-for="task in toDoTasks" :key="task.id"
                 class="relative w-full h-48 bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-md p-4 flex items-center justify-center text-center cursor-pointer transition"
                 @click.stop="openTaskMenu(task)">
              <div class="text-lg font-semibold text-gray-800 break-words">
                {{ task.title }}
              </div>
              <!-- Contextual Menu for Task -->
              <div v-if="task.showMenu" class="absolute top-0 right-0 mt-2 mr-2 bg-white shadow-lg border border-gray-200 rounded-lg">
                <button @click="moveTaskToStatus(task, 'BACKLOG')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to Backlog</button>
                <button @click="moveTaskToStatus(task, 'PROGRESS')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to In Progress</button>
                <button @click="assignTask(task)" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Assign Task</button>
                <button @click="deleteTask(task)" class="px-4 py-2 text-sm text-red-600 hover:bg-red-100">Delete Task</button>
              </div>
            </div>
          </div>
          <button @click="addNewTask('TODO')" class="mt-4 bg-yellow-600 text-white rounded-full w-12 h-12 flex items-center justify-center text-2xl hover:bg-yellow-700 transition duration-200">
            +
          </button>
        </div>
  
        <!-- In Progress Column -->
        <div class="flex flex-col min-w-[200px]">
          <h2 class="text-xl font-bold mb-2 text-center text-indigo-600">In Progress</h2>
          <div class="flex flex-col gap-4">
            <div v-for="task in inProgressTasks" :key="task.id"
                 class="relative w-full h-48 bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-md p-4 flex items-center justify-center text-center cursor-pointer transition"
                 @click.stop="openTaskMenu(task)">
              <div class="text-lg font-semibold text-gray-800 break-words">
                {{ task.title }}
              </div>
              <!-- Contextual Menu for Task -->
              <div v-if="task.showMenu" class="absolute top-0 right-0 mt-2 mr-2 bg-white shadow-lg border border-gray-200 rounded-lg">
                <button @click="moveTaskToStatus(task, 'TODO')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to TODO</button>
                <button @click="moveTaskToStatus(task, 'REVIEW')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to Review</button>
                <button @click="assignTask(task)" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Assign Task</button>
                <button @click="deleteTask(task)" class="px-4 py-2 text-sm text-red-600 hover:bg-red-100">Delete Task</button>
              </div>
            </div>
          </div>
          <button @click="addNewTask('PROGRESS')" class="mt-4 bg-indigo-600 text-white rounded-full w-12 h-12 flex items-center justify-center text-2xl hover:bg-indigo-700 transition duration-200">
            +
          </button>
        </div>
  
        <!-- In Review Column -->
        <div class="flex flex-col min-w-[200px]">
          <h2 class="text-xl font-bold mb-2 text-center text-purple-600">In Review</h2>
          <div class="flex flex-col gap-4">
            <div v-for="task in inReviewTasks" :key="task.id"
                 class="relative w-full h-48 bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-md p-4 flex items-center justify-center text-center cursor-pointer transition"
                 @click.stop="openTaskMenu(task)">
              <div class="text-lg font-semibold text-gray-800 break-words">
                {{ task.title }}
              </div>
              <!-- Contextual Menu for Task -->
              <div v-if="task.showMenu" class="absolute top-0 right-0 mt-2 mr-2 bg-white shadow-lg border border-gray-200 rounded-lg">
                <button @click="moveTaskToStatus(task, 'PROGRESS')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to In Progress</button>
                <button @click="moveTaskToStatus(task, 'DONE')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to Done</button>
                <button @click="assignTask(task)" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Assign Task</button>
                <button @click="deleteTask(task)" class="px-4 py-2 text-sm text-red-600 hover:bg-red-100">Delete Task</button>
              </div>
            </div>
          </div>
          <button @click="addNewTask('REVIEW')" class="mt-4 bg-purple-600 text-white rounded-full w-12 h-12 flex items-center justify-center text-2xl hover:bg-purple-700 transition duration-200">
            +
          </button>
        </div>
  
        <!-- Done Column -->
        <div class="flex flex-col min-w-[200px]">
          <h2 class="text-xl font-bold mb-2 text-center text-green-600">Done</h2>
          <div class="flex flex-col gap-4">
            <div v-for="task in doneTasks" :key="task.id"
                 class="relative w-full h-48 bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-md p-4 flex items-center justify-center text-center cursor-pointer transition"
                 @click.stop="openTaskMenu(task)">
              <div class="text-lg font-semibold text-gray-800 break-words">
                {{ task.title }}
              </div>
              <!-- Contextual Menu for Task -->
              <div v-if="task.showMenu" class="absolute top-0 right-0 mt-2 mr-2 bg-white shadow-lg border border-gray-200 rounded-lg">
                <button @click="moveTaskToStatus(task, 'REVIEW')" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Move to Review</button>
                <button @click="assignTask(task)" class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Assign Task</button>
                <button @click="deleteTask(task)" class="px-4 py-2 text-sm text-red-600 hover:bg-red-100">Delete Task</button>
              </div>
            </div>
          </div>
          <button @click="addNewTask('DONE')" class="mt-4 bg-green-600 text-white rounded-full w-12 h-12 flex items-center justify-center text-2xl hover:bg-green-700 transition duration-200">
            +
          </button>
        </div>
  
      </div>
    </div>
  </template>
  
  <script setup>
  import axios from 'axios';
  import { onMounted, onUnmounted, ref } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  
  const route = useRoute();
  const router = useRouter();
  
  const projectId = route.params.id;
  
  const backlogTasks = ref([]);
  const toDoTasks = ref([]);
  const inProgressTasks = ref([]);
  const inReviewTasks = ref([]);
  const doneTasks = ref([]);
  
  // Check that projectId is a valid number, otherwise go back to dashboard
  if (isNaN(Number(projectId))) {
    console.error('Invalid projectId:', projectId);
  router.push('/dashboard');
  }
  
  const closeAllTaskMenus = () => {
  [...backlogTasks.value, ...toDoTasks.value, ...inProgressTasks.value, ...inReviewTasks.value, ...doneTasks.value].forEach((task) => {
  task.showMenu = false;
  });
  };
  
  const openTaskMenu = (clickedTask) => {
  closeAllTaskMenus();
  clickedTask.showMenu = true;
  };
  
  const handleClickOutside = (event) => {
  if (!event.target.closest('.relative')) {
  closeAllTaskMenus();
  }
  };
  
  const moveTaskToStatus = (task, status) => {
  task.status = status;
  axios.put(`${import.meta.env.VITE_API_BASE_URL}/tasks/${task.id}`, { status: status }).then(() => fetchTasks());
  };
  
  const assignTask = (task) => {
  alert('Assigning task: ' + task.title);
  };
  
  const deleteTask = (task) => {
  axios.delete(`${import.meta.env.VITE_API_BASE_URL}/tasks/${task.id}`).then(() => fetchTasks());
  };
  
  const addNewTask = (status) => {
  alert(`Adding a new task to ${status} column`);
  };
  
  const goToDashboard = () => {
  router.push('/dashboard');
  };
  
  const fetchTasks = async () => {
  const token = localStorage.getItem('authToken');
  if (!token) {
  router.push('/login');
  return;
  }
  
  backlogTasks.value = [];
  toDoTasks.value = [];
  inProgressTasks.value = [];
  inReviewTasks.value = [];
  doneTasks.value = [];
  
  try {
  const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/tasks/project/${projectId}`, {
  headers: { Authorization: `Bearer ${token}` },
  });
  
  response.data.forEach((task) => {
    task.showMenu = false;
    switch (task.status) {
      case 'BACKLOG':
        backlogTasks.value.push(task);
        break;
      case 'TODO':
        toDoTasks.value.push(task);
        break;
      case 'PROGRESS':
        inProgressTasks.value.push(task);
        break;
      case 'REVIEW':
        inReviewTasks.value.push(task);
        break;
      case 'DONE':
        doneTasks.value.push(task);
        break;
    }
  });
  
  } catch (error) {
        console.error('Failed to fetch tasks:', error);
        if (error.response?.status === 401) {
        localStorage.removeItem('authToken');
        router.push('/login');
        }
  }
  };
  
  onMounted(() => {
  document.addEventListener('click', handleClickOutside);
  fetchTasks();
  });
  onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
  });
  </script>