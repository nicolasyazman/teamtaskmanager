<script setup>
import LandingPage from './views/LandingPage.vue'
</script>

<template>
  <div>
    <nav class="bg-gray-800 text-white p-4 flex justify-between">
      <div class="font-bold text-xl">TeamTaskManager</div>
      <div class="space-x-4">
        <router-link to="/" class="hover:underline">Home</router-link>
        <router-link to="/register" class="hover:underline">Register</router-link>
        <router-link to="/login" class="hover:underline">Login</router-link>
      </div>
    </nav>
    <router-view />
  </div>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
