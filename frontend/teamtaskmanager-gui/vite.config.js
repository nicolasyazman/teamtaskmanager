import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vitest/config'
import tailwindcss from '@tailwindcss/vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig({
  plugins: [
    tailwindcss(),
    vue(),
    vueDevTools(),
  ],
  test: {
    environment: 'jsdom',
    globals: true,
    define: {
      'import.meta.env': {
        VITE_API_BASE_URL: 'http://localhost:8080'
      }
    },
    coverage: {
     /* thresholds: {
        lines: 60,
        branches: 60,
        functions: 60,
        statements: 60
      },*/
      exclude: ['**/*.?(c|m)[jt]s?(x)'],

      reporter: ['text', 'json-summary', 'json'],
      
      reportOnFailure: true,
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
   proxy: {
      '/api': {
        target: 'http://backend:8080',   // or 'http://localhost:8080' for local dev without Docker
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''), // remove /api prefix when forwarding
      }
    }
  },
})
